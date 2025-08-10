package com.example.celestic.manager


import android.content.Context
import android.util.Log
import org.json.JSONObject
import org.opencv.core.CvType
import org.opencv.core.Mat
import org.opencv.core.Size
import java.io.File
import java.io.FileInputStream
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject

class CalibrationManager @Inject constructor(private val context: Context) {

    var cameraMatrix: Mat? = null
    var distortionCoeffs: Mat? = null
    var resolution: Pair<Int, Int>? = null
    var calibrationDate: String? = null

    private val calibrationFile = File(context.filesDir, "config/calibration.json")

    init {
        loadCalibration()
    }

    private fun loadCalibration(): Boolean {
        return try {
            val json =
                JSONObject(FileInputStream(calibrationFile).bufferedReader().use { it.readText() })

            // Parse cameraMatrix
            val matrixArray = json.getJSONArray("cameraMatrix")
            val matrix = Mat(3, 3, CvType.CV_64F)
            for (i in 0 until 3) {
                val row = matrixArray.getJSONArray(i)
                for (j in 0 until 3) {
                    matrix.put(i, j, row.getDouble(j))
                }
            }

            // Parse distortionCoeffs
            val coeffsArray = json.getJSONArray("distortionCoeffs")
            val coeffs = Mat(1, coeffsArray.length(), CvType.CV_64F)
            for (i in 0 until coeffsArray.length()) {
                coeffs.put(0, i, coeffsArray.getDouble(i))
            }

            // Resolution
            val resArray = json.getJSONArray("resolution")
            resolution = Pair(resArray.getInt(0), resArray.getInt(1))

            // Metadata
            calibrationDate = json.getString("calibrationDate")

            cameraMatrix = matrix
            distortionCoeffs = coeffs

            true
        } catch (e: Exception) {
            Log.e("CalibrationManager", "Error al cargar calibración", e)
            false
        }
    }

    fun getScaleFactor(pixelLength: Double): Double {
        // Ejemplo simple: escalar 1px ≈ X mm (reemplazar con lógica real si tienes datos de referencia)
        val focalLength = cameraMatrix?.get(0, 0)?.firstOrNull() ?: return 0.0
        val mmPerPixel = 1.0 / focalLength // Sujeto a corrección según distancia focal real
        return pixelLength * mmPerPixel
    }

    fun detectCharucoPattern(image: Mat): Mat {
        val dictionary =
            org.opencv.aruco.Aruco.getPredefinedDictionary(org.opencv.aruco.Aruco.DICT_6X6_250)
        val corners = ArrayList<Mat>()
        val ids = Mat()
        org.opencv.aruco.Aruco.detectMarkers(image, dictionary, corners, ids)
        val charucoCorners = Mat()
        val charucoIds = Mat()
        if (ids.total() > 0) {
            val board = org.opencv.aruco.CharucoBoard.create(5, 7, 0.04f, 0.02f, dictionary)
            org.opencv.aruco.Aruco.interpolateCornersCharuco(
                corners,
                ids,
                image,
                board,
                charucoCorners,
                charucoIds
            )
            if (charucoCorners.total() > 0) {
                val gray = Mat()
                Imgproc.cvtColor(image, gray, Imgproc.COLOR_BGR2GRAY)
                val term = TermCriteria(TermCriteria.EPS or TermCriteria.MAX_ITER, 30, 0.1)
                val corners2f = MatOfPoint2f(*charucoCorners.toArray())
                Imgproc.cornerSubPix(gray, corners2f, Size(5.0, 5.0), Size(-1.0, -1.0), term)
                charucoCorners.fromArray(*corners2f.toArray())
            }
        }
        return charucoCorners
    }

    fun generateCalibrationMatrix(
        charucoCorners: List<Mat>,
        charucoIds: List<Mat>,
        imageSize: Size,
    ): Mat {
        val cameraMatrix = Mat()
        val distCoeffs = Mat()
        val rvecs = ArrayList<Mat>()
        val tvecs = ArrayList<Mat>()
        val board = org.opencv.aruco.CharucoBoard.create(
            5,
            7,
            0.04f,
            0.02f,
            org.opencv.aruco.Aruco.getPredefinedDictionary(org.opencv.aruco.Aruco.DICT_6X6_250)
        )
        org.opencv.aruco.Aruco.calibrateCameraCharuco(
            charucoCorners,
            charucoIds,
            board,
            imageSize,
            cameraMatrix,
            distCoeffs,
            rvecs,
            tvecs
        )
        return cameraMatrix
    }

    fun saveCalibrationToJson(
        cameraMatrix: Mat,
        distortionCoeffs: Mat,
        resolution: Pair<Int, Int>,
    ) {
        val json = JSONObject()
        json.put("cameraMatrix", cameraMatrix.dump())
        json.put("distortionCoeffs", distortionCoeffs.dump())
        json.put("resolution", resolution)
        json.put("calibrationDate", SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date()))
        calibrationFile.writeText(json.toString())
    }

    fun generateCalibrationMatrixFromImages(images: List<Mat>): Mat {
        val allCorners = ArrayList<Mat>()
        val allIds = ArrayList<Mat>()
        val imageSize = images[0].size()
        for (image in images) {
            val corners = detectCharucoPattern(image)
            if (corners.total() > 0) {
                allCorners.add(corners)
                val ids = Mat()
                // TODO: Get ids from detectCharucoPattern
                allIds.add(ids)
            }
        }
        return generateCalibrationMatrix(allCorners, allIds, imageSize)
    }
}
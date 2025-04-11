package com.ejemplo.opencvdetector

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.opencv.android.CameraBridgeViewBase
import org.opencv.android.JavaCameraView
import org.opencv.android.OpenCVLoader
import org.opencv.core.*
import org.opencv.imgproc.Imgproc

class MainActivity : ComponentActivity(), CameraBridgeViewBase.CvCameraViewListener2 {

    private lateinit var mOpenCvCameraView: JavaCameraView
    private val detectionItems = mutableListOf<DetectionItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // 1. Crea un NavController
            val navController = rememberNavController()

            // 2. Define el NavHost con rutas
            NavHost(navController = navController, startDestination = "camera") {
                // Pantalla principal (CameraView)
                composable("camera") {
                    CameraView(navController = navController) // Pasa el navController aquí
                }

                // Pantalla de detalle del agujero
                composable("detalleAgujero/{index}") { backStackEntry ->
                    // Recuperar el índice pasado como parámetro
                    val index = backStackEntry.arguments?.getString("index")?.toIntOrNull() ?: 0
                    DetalleAgujeroScreen(index = index, detectionItems = detectionItems)
                }
            }
        }

        // Inicialización de OpenCV
        if (!OpenCVLoader.initDebug()) {
            Log.e("OpenCV", "Error al inicializar OpenCV.")
            Toast.makeText(this, "Error al inicializar OpenCV", Toast.LENGTH_SHORT).show()
        } else {
            Log.d("OpenCV", "OpenCV inicializado correctamente.")
        }
    }

    // Modelo de datos para lámina, agujeros y avellanados
    data class DetectionItem(
        val type: String, // "lamina", "agujero", "avellanado"
        val position: Point? = null,
        val width: Int? = null,
        val height: Int? = null,
        val diameter: Int? = null
    )

    @Composable
    fun CameraView(navController: NavController) {
        AndroidView(
            factory = { context ->
                JavaCameraView(context).apply {
                    setCvCameraViewListener(this@MainActivity)
                    visibility = View.VISIBLE
                }
            },
            modifier = Modifier.fillMaxSize()
        )
    }

    override fun onCameraFrame(inputFrame: CameraBridgeViewBase.CvCameraViewFrame?): Mat {
        inputFrame?.let {
            val rgba = inputFrame.rgba()
            detectionItems.clear() // Limpiar detecciones por cada frame
            detectSteelSheet(rgba)
            return rgba
        }
        return Mat()
    }

    override fun onCameraViewStarted(width: Int, height: Int) {}
    override fun onCameraViewStopped() {}

    private fun detectSteelSheet(frame: Mat) {
        val gray = Mat()
        Imgproc.cvtColor(frame, gray, Imgproc.COLOR_RGBA2GRAY)
        Imgproc.bilateralFilter(gray, gray, 9, 75.0, 75.0)

        val edges = Mat()
        Imgproc.Canny(gray, edges, 50.0, 150.0)

        val contours = mutableListOf<MatOfPoint>()
        Imgproc.findContours(
            edges,
            contours,
            Mat(),
            Imgproc.RETR_EXTERNAL,
            Imgproc.CHAIN_APPROX_SIMPLE
        )

        var steelSheetRect: Rect? = null
        for (contour in contours) {
            val epsilon = 0.04 * Imgproc.arcLength(MatOfPoint2f(*contour.toArray()), true)
            val approx = MatOfPoint()
            Imgproc.approxPolyDP(
                MatOfPoint2f(*contour.toArray()),
                MatOfPoint2f(),
                epsilon,
                true
            ).toArray().let {
                approx.fromArray(*it)
            }

            if (approx.total() == 4L) {
                val rect = Imgproc.boundingRect(approx)
                if (steelSheetRect == null || rect.area() > steelSheetRect!!.area()) {
                    steelSheetRect = rect
                }
            }
        }

        steelSheetRect?.let {
            Imgproc.rectangle(frame, it.tl(), it.br(), Scalar(255.0, 0.0, 0.0), 3)
            val width = it.width
            val height = it.height

            Imgproc.putText(
                frame, "Lámina: $width x $height px",
                Point(it.x + 10, it.y + 10),
                Imgproc.FONT_HERSHEY_SIMPLEX, 1.0,
                Scalar(0.0, 255.0, 0.0), 2
            )

            detectionItems.add(
                DetectionItem(type = "lamina", width = width, height = height)
            )

            Log.d("Lámina", "Ancho: $width px, Alto: $height px")
        }

        detectHoles(frame, gray)
    }

    private fun detectHoles(frame: Mat, gray: Mat) {
        val circles = Mat()

        Imgproc.HoughCircles(
            gray, circles, Imgproc.CV_HOUGH_GRADIENT, 1.0, gray.rows() / 4.0,
            100.0, 30.0, 10, 50
        )

        if (circles.cols() > 0) {
            val detectedCircles =
                mutableListOf<Triple<Point, Int, Int>>() // centro, radio, index

            for (i in 0 until circles.cols()) {
                val data = circles.get(0, i)
                val center = Point(data[0], data[1])
                val radius = data[2].toInt()

                detectedCircles.add(Triple(center, radius, i))

                // Dibujar círculo principal
                Imgproc.circle(frame, center, radius, Scalar(0.0, 255.0, 0.0), 2)
                Imgproc.circle(frame, center, 3, Scalar(0.0, 0.0, 255.0), 2)

                val diameter = radius * 2
                detectionItems.add(
                    DetectionItem(type = "agujero", position = center, diameter = diameter)
                )

                Imgproc.putText(
                    frame, "Ø $diameter px",
                    Point(center.x + 10, center.y + 10),
                    Imgproc.FONT_HERSHEY_SIMPLEX, 0.7,
                    Scalar(0.0, 255.0, 0.0), 2
                )
            }

            // Detectar avellanado
            detectCounters(detectedCircles, frame)
        }
    }

    private fun detectCounters(circles: List<Triple<Point, Int, Int>>, frame: Mat) {
        // Lógica de detección de avellanado
        for ((i, triple) in circles.withIndex()) {
            val (center, radius, _) = triple
            for ((j, triple2) in circles.withIndex()) {
                if (i == j) continue
                val (center2, radius2, _) = triple2

                if (Math.abs(center.x - center2.x) < 5 &&
                    Math.abs(center.y - center2.y) < 5 &&
                    radius2 > radius + 5
                ) {
                    detectionItems.add(
                        DetectionItem(
                            type = "avellanado",
                            position = center,
                            diameter = radius2 * 2
                        )
                    )
                    Imgproc.putText(
                        frame, "Avellanado Ø ${radius2 * 2}px",
                        Point(center.x + 10, center.y + 30),
                        Imgproc.FONT_HERSHEY_SIMPLEX, 0.6,
                        Scalar(255.0, 0.0, 255.0), 2
                    )
                    break
                }
            }
        }
    }
}

@Composable
fun DetalleAgujeroScreen(index: Int, detectionItems: List<DetectionItem>) {
    // Obtener el item del agujero seleccionado usando el índice
    val item = detectionItems.getOrNull(index)

    if (item != null) {
        // Mostrar los detalles
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text("Detalle del Agujero", style = MaterialTheme.typography.h4)
            Spacer(modifier = Modifier.height(20.dp))

            Text("Tipo: ${item.type}")
            Text("Diámetro: ${item.diameter} px")
            Text("Posición: (${item.position?.x}, ${item.position?.y})")

            // Si hay avellanado, también lo podemos mostrar
            if (item.type == "avellanado") {
                Text("Avellanado: Si")
            }
        }
    } else {
        Text("Agujero no encontrado", style = MaterialTheme.typography.h6)
    }
}

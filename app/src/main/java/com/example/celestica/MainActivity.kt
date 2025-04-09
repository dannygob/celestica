package com.example.celestica

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.opencv.android.CameraBridgeViewBase
import org.opencv.android.JavaCameraView
import org.opencv.android.OpenCVLoader
import org.opencv.core.*
import org.opencv.imgproc.Imgproc
import androidx.compose.ui.viewinterop.AndroidView

class MainActivity : ComponentActivity(), CameraBridgeViewBase.CvCameraViewListener2 {

    private lateinit var mOpenCvCameraView: JavaCameraView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                CameraView()
            }
        }

        if (!OpenCVLoader.initDebug()) {
            Log.e("OpenCV", "Error al inicializar OpenCV.")
            Toast.makeText(this, "Error al inicializar OpenCV", Toast.LENGTH_SHORT).show()
        } else {
            Log.d("OpenCV", "OpenCV inicializado correctamente.")
        }
    }

    @Composable
    fun CameraView() {
        AndroidView(
            factory = { context ->
                JavaCameraView(context).apply {
                    setCvCameraViewListener(this@MainActivity)
                }
            },
            modifier = Modifier.fillMaxSize()
        )
    }

    override fun onCameraFrame(inputFrame: CameraBridgeViewBase.CvCameraViewFrame?): Mat {
        val rgba = inputFrame!!.rgba()

        // Procesamiento: Detección de la lámina y agujeros
        detectSteelSheet(rgba)

        return rgba
    }

    override fun onCameraViewStarted(width: Int, height: Int) {}
    override fun onCameraViewStopped() {}

    private fun detectSteelSheet(frame: Mat) {
        val gray = Mat()
        Imgproc.cvtColor(frame, gray, Imgproc.COLOR_RGBA2GRAY)

        // Reducción de ruido
        Imgproc.GaussianBlur(gray, gray, Size(5.0, 5.0), 0.0)

        // Detectar bordes con Canny
        val edges = Mat()
        Imgproc.Canny(gray, edges, 50.0, 150.0)

        // Encontrar contornos
        val contours = mutableListOf<MatOfPoint>()
        Imgproc.findContours(edges, contours, Mat(), Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE)

        for (contour in contours) {
            val rect = Imgproc.boundingRect(contour)

            // Dibujar el contorno de la lámina detectada
            Imgproc.rectangle(frame, rect.tl(), rect.br(), Scalar(255.0, 0.0, 0.0), 3)

            val width = rect.width
            val height = rect.height

            Log.d("Steel Sheet", "Ancho: $width px, Alto: $height px")
        }

        // Detectar agujeros
        detectHoles(frame, gray)
    }

    private fun detectHoles(frame: Mat, gray: Mat) {
        val circles = Mat()

        // Ajuste de parámetros para HoughCircles
        Imgproc.HoughCircles(
            gray, circles, Imgproc.CV_HOUGH_GRADIENT, 1.0, gray.rows() / 4.0,
            100.0, 30.0, 10, 50 // Estos parámetros pueden necesitar ajustes según tu caso
        )

        if (circles.cols() > 0) {
            for (i in 0 until circles.cols()) {
                val data = circles.get(0, i)
                val center = Point(data[0], data[1])
                val radius = data[2].toInt()

                // Dibujar cada agujero
                Imgproc.circle(frame, center, radius, Scalar(0.0, 255.0, 0.0), 2) // Verde
                Imgproc.circle(frame, center, 3, Scalar(0.0, 0.0, 255.0), 2) // Rojo

                val diameter = radius * 2
                Log.d("Hole", "Centro: $center, Diámetro: $diametro px")
            }
        }
    }
}

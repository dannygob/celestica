package com.example.celestica.manager

import android.content.Context
import android.graphics.Bitmap
import androidx.core.graphics.createBitmap
import androidx.core.graphics.get
import androidx.core.graphics.scale
import org.tensorflow.lite.Interpreter
import java.io.FileInputStream
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.channels.FileChannel

class ImageClassifier(context: Context) {

    private val modelFileName = "mobilenet_v2.tflite"
    private val inputImageSize = 224
    private val numChannels = 3
    private val numClasses = 1001

    private val interpreter: Interpreter

    init {
        val assetFileDescriptor = context.assets.openFd(modelFileName)
        val inputStream = FileInputStream(assetFileDescriptor.fileDescriptor)
        val fileChannel = inputStream.channel
        val startOffset = assetFileDescriptor.startOffset
        val declaredLength = assetFileDescriptor.declaredLength
        val modelBuffer =
            fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)
        interpreter = Interpreter(modelBuffer)
    }

    fun runInference(imageProxy: Bitmap): FloatArray {
        val inputBuffer = convertImageProxyToByteBuffer(imageProxy)
        val output = Array(1) { FloatArray(numClasses) }
        interpreter.run(inputBuffer, output)
        return output[0]
    }

    private fun convertImageProxyToByteBuffer(image: Bitmap): ByteBuffer {
        val byteBuffer =
            ByteBuffer.allocateDirect(4 * inputImageSize * inputImageSize * numChannels)
        byteBuffer.order(ByteOrder.nativeOrder())

        val yBuffer = image.planes[0].buffer
        val uBuffer = image.planes[1].buffer
        val vBuffer = image.planes[2].buffer

        val ySize = yBuffer.remaining()
        val uSize = uBuffer.remaining()
        val vSize = vBuffer.remaining()

        val nv21 = ByteArray(ySize + uSize + vSize)

        yBuffer.get(nv21, 0, ySize)
        vBuffer.get(nv21, ySize, vSize)
        uBuffer.get(nv21, ySize + vSize, uSize)

        val yuvImage = org.opencv.core.Mat(
            image.height + image.height / 2,
            image.width,
            org.opencv.core.CvType.CV_8UC1
        )
        yuvImage.put(0, 0, nv21)
        val rgbImage = org.opencv.core.Mat()
        org.opencv.imgproc.Imgproc.cvtColor(
            yuvImage,
            rgbImage,
            org.opencv.imgproc.Imgproc.COLOR_YUV2RGB_NV21
        )

        val bitmap = createBitmap(rgbImage.cols(), rgbImage.rows())
        org.opencv.android.Utils.matToBitmap(rgbImage, bitmap)

        val resizedBitmap = bitmap.scale(inputImageSize, inputImageSize)

        for (y in 0 until inputImageSize) {
            for (x in 0 until inputImageSize) {
                val pixel = resizedBitmap[x, y]
                val r = (pixel shr 16 and 0xFF) / 255f
                val g = (pixel shr 8 and 0xFF) / 255f
                val b = (pixel and 0xFF) / 255f

                byteBuffer.putFloat(r)
                byteBuffer.putFloat(g)
                byteBuffer.putFloat(b)
            }
        }

        return byteBuffer
    }

    fun mapPredictionToFeatureType(predictions: FloatArray): String {
        val maxIndex =
            predictions.indices.maxByOrNull { predictions[it] } ?: return "Clase desconocida"
        return when (maxIndex) {
            in 0..100 -> "Defecto superficial"
            in 101..500 -> "Curvatura irregular"
            in 501 until numClasses -> "Pieza sin defecto"
            else -> "Clase desconocida"
        }
    }
}
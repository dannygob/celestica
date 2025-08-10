package com.example.celestica.ui.scanner


import android.graphics.Bitmap
import org.opencv.android.Utils
import org.opencv.core.Mat
import org.opencv.objdetect.QRCodeDetector

object QRScanner {

    fun startQrScan(bitmap: Bitmap): String? {
        val mat = Mat()
        Utils.bitmapToMat(bitmap, mat)
        return decodeBarcode(mat)
    }

    fun decodeBarcode(mat: Mat): String? {
        val detector = QRCodeDetector()
        return detector.detectAndDecode(mat).takeIf { it.isNotBlank() }
    }
}
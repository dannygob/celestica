package com.example.celestica.utils

import android.content.Context
import android.util.Log
import org.opencv.android.OpenCVLoader

object OpenCVInitializer {

    fun initOpenCV(context: Context) {
        if (OpenCVLoader.initDebug()) {
            Log.d("OpenCV", "OpenCV initialized successfully")
        } else {
            Log.e("OpenCV", "OpenCV initialization failed")
        }
    }
}

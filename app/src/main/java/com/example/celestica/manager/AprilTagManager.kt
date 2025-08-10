package com.example.celestic.manager

import com.taylor.demo.apriltag.ApriltagNative
import org.opencv.core.Mat

class AprilTagManager {

    data class Marker(
        val id: Int,
        val hamming: Int,
        val decisionMargin: Float,
        val center: DoubleArray,
        val corners: DoubleArray,
    )

    fun init() {
        ApriltagNative.apriltagInit("tag36h11", 1, 1.0f, 0.0f, 4, 1, 0.25)
    }

    fun detectMarkers(image: Mat): List<Marker> {
        val yuvData = ByteArray(image.total().toInt() * image.channels())
        image.get(0, 0, yuvData)
        val detections = ApriltagNative.apriltagDetectYuv(
            yuvData,
            "rear_camera",
            0,
            1,
            image.width(),
            image.height()
        )
        val markers = mutableListOf<Marker>()
        if (detections != null) {
            for (detection in detections) {
                markers.add(
                    Marker(
                        detection.id,
                        detection.hamming,
                        detection.decisionMargin,
                        detection.center,
                        detection.corners
                    )
                )
            }
        }
        return markers
    }
}
package com.example.celestic.manager


import org.opencv.aruco.Aruco
import org.opencv.core.Mat
import org.opencv.core.MatOfInt

class ArUcoManager {

    data class Marker(val id: Int, val corners: Mat)

    fun detectMarkers(image: Mat): List<Marker> {
        val dictionary = Aruco.getPredefinedDictionary(Aruco.DICT_6X6_250)
        val corners = ArrayList<Mat>()
        val ids = MatOfInt()
        Aruco.detectMarkers(image, dictionary, corners, ids)

        val markers = mutableListOf<Marker>()
        if (ids.total() > 0) {
            val idsArray = IntArray(ids.total().toInt())
            ids.get(0, 0, idsArray)
            for (i in idsArray.indices) {
                markers.add(Marker(idsArray[i], corners[i]))
            }
        }
        return markers
    }
}

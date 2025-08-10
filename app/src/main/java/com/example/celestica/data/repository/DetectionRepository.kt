package com.example.celestic.data.repository

import com.example.celestic.data.dao.CelesticDao
import com.example.celestic.models.DetectionItem
import com.example.celestic.models.calibration.DetectedFeature

class DetectionRepository(private val dao: CelesticDao) {

    suspend fun saveDetection(detection: DetectedFeature) {
        dao.insertDetection(detection)
    }

    suspend fun saveDetections(detections: List<DetectedFeature>) {
        dao.insertDetections(detections)
    }

    fun loadDetections(): kotlinx.coroutines.flow.Flow<List<DetectedFeature>> {
        return dao.getAllDetections()
    }

    suspend fun clearAllDetections() {
        dao.clearDetections()
    }

    suspend fun insertDetection(item: DetectionItem) = dao.insert(item)

    suspend fun deleteDetection(item: DetectionItem) = dao.delete(item)

    suspend fun insertCameraCalibrationData(cameraCalibrationData: com.example.celestic.models.calibration.CameraCalibrationData) {
        dao.insertCameraCalibrationData(cameraCalibrationData)
    }

    fun getCameraCalibrationData(): kotlinx.coroutines.flow.Flow<com.example.celestic.models.calibration.CameraCalibrationData?> {
        return dao.getCameraCalibrationData()
    }

    suspend fun insertReportConfig(reportConfig: com.example.celestic.models.report.ReportConfig) {
        dao.insertReportConfig(reportConfig)
    }

    fun getReportConfig(): kotlinx.coroutines.flow.Flow<com.example.celestic.models.report.ReportConfig?> {
        return dao.getReportConfig()
    }

    fun getAll(): kotlinx.coroutines.flow.Flow<List<DetectionItem>> {
        return dao.getAll()
    }

    fun getFeaturesForDetection(detectionItemId: Long): kotlinx.coroutines.flow.Flow<List<DetectedFeature>> {
        return dao.getFeaturesForDetection(detectionItemId)
    }

    suspend fun startInspection(): Long {
        val inspection =
            com.example.celestic.models.Inspection(timestamp = System.currentTimeMillis())
        return dao.insertInspection(inspection)
    }

    fun getAllInspections(): kotlinx.coroutines.flow.Flow<List<com.example.celestic.models.Inspection>> {
        return dao.getAllInspections()
    }
}
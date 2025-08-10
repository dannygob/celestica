package com.example.celestica.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.celestica.models.DetectionItem
import com.example.celestica.models.Inspection
import com.example.celestica.models.calibration.CameraCalibrationData
import com.example.celestica.models.calibration.DetectedFeature
import com.example.celestica.models.report.ReportConfig
import kotlinx.coroutines.flow.Flow

@Dao
interface CelesticDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: DetectionItem)

    @Query("SELECT * FROM detection_items ORDER BY timestamp DESC")
    fun getAll(): Flow<List<DetectionItem>>

    @Delete
    suspend fun delete(item: DetectionItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDetection(detection: DetectedFeature)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDetections(detections: List<DetectedFeature>)

    @Query("SELECT * FROM detected_features ORDER BY timestamp DESC")
    fun getAllDetections(): Flow<List<DetectedFeature>>

    @Query("DELETE FROM detected_features")
    suspend fun clearDetections()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCameraCalibrationData(cameraCalibrationData: CameraCalibrationData)

    @Query("SELECT * FROM camera_calibration ORDER BY id DESC LIMIT 1")
    fun getCameraCalibrationData(): Flow<CameraCalibrationData?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReportConfig(reportConfig: ReportConfig)

    @Query("SELECT * FROM report_config ORDER BY id DESC LIMIT 1")
    fun getReportConfig(): Flow<ReportConfig?>

    @Query("SELECT * FROM detected_features WHERE detectionItemId = :detectionItemId")
    fun getFeaturesForDetection(detectionItemId: Long): Flow<List<DetectedFeature>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInspection(inspection: Inspection): Long

    @Query("SELECT * FROM inspections ORDER BY timestamp DESC")
    fun getAllInspections(): Flow<List<Inspection>>
}
package com.example.celestica.models.calibration

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

/**
 * Representa los parámetros de calibración obtenidos mediante patrón Charuco.
 * Se guarda en Room para reutilización y validación.
 */
@Parcelize
@Entity(tableName = "camera_calibration")
data class CameraCalibrationData(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,

    @ColumnInfo(name = "camera_matrix")
    val cameraMatrix: String, // Se almacena como JSON string serializado

    @ColumnInfo(name = "distortion_coeffs")
    val distortionCoeffs: String, // También como JSON string

    @ColumnInfo(name = "resolution_width")
    val resolutionWidth: Int,

    @ColumnInfo(name = "resolution_height")
    val resolutionHeight: Int,

    @ColumnInfo(name = "calibration_date")
    val calibrationDate: String, // "YYYY-MM-DD HH:mm"
) : Parcelable
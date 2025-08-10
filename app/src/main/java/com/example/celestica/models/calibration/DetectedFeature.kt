package com.example.celestic.models.calibration

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

/**
 * Representa una característica visual detectada en una imagen calibrada.
 */
@Parcelize
@Entity(tableName = "detected_features")
data class DetectedFeature(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,

    @ColumnInfo(name = "detection_item_id")
    val detectionItemId: Long,

    @ColumnInfo(name = "feature_type")
    val featureType: String, // Ej: "charuco_corner", "aruco_marker", "edge", etc.

    @ColumnInfo(name = "x_coord")
    val xCoord: Float,

    @ColumnInfo(name = "y_coord")
    val yCoord: Float,

    @ColumnInfo(name = "confidence")
    val confidence: Float, // Valor de certeza de detección, 0.0–1.0

    @ColumnInfo(name = "timestamp")
    val timestamp: Long, // Epoch time en milisegundos

    @ColumnInfo(name = "measurements")
    val measurements: Map<String, Float> = emptyMap(),
) : Parcelable
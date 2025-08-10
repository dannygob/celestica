package com.example.celestic.models.report


import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.celestic.models.enums.DetectionStatus
import com.example.celestic.models.enums.DetectionType
import kotlinx.parcelize.Parcelize

/**
 * Representa una entrada en el historial de inspecciones o reporte generado.
 */
@Entity(tableName = "report_entries")
@Parcelize
data class ReportEntry(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,

    @ColumnInfo(name = "frame_id") val frameId: String,
    @ColumnInfo(name = "timestamp") val timestamp: Long,

    @ColumnInfo(name = "detected_type") val type: DetectionType,
    @ColumnInfo(name = "status") val status: DetectionStatus,
    @ColumnInfo(name = "confidence") val confidence: Float,

    @ColumnInfo(name = "measurement_mm") val measurementMm: Float?,
    @ColumnInfo(name = "notes") val notes: String = "",
) : Parcelable
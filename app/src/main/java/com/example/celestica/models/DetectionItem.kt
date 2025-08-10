package com.example.celestic.models

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.celestic.models.enums.DetectionStatus
import com.example.celestic.models.enums.DetectionType
import kotlinx.parcelize.Parcelize

@Entity(
    tableName = "detection_items",
    foreignKeys = [
        androidx.room.ForeignKey(
            entity = Inspection::class,
            parentColumns = ["id"],
            childColumns = ["inspectionId"],
            onDelete = androidx.room.ForeignKey.CASCADE
        )
    ]
)
@Parcelize
data class DetectionItem(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val inspectionId: Long,
    val frameId: String,
    val type: DetectionType,
    @Embedded val boundingBox: com.example.celestic.models.geometry.BoundingBox,
    val confidence: Float,
    val status: DetectionStatus,
    val measurementMm: Float? = null,
    val timestamp: Long,
    val linkedQrCode: String? = null,
    val notes: String = "",
) : Parcelable


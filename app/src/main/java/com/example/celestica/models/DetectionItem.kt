package com.example.celestica.models

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.celestica.models.enums.DetectionStatus
import com.example.celestica.models.enums.DetectionType
import com.example.celestica.models.geometry.BoundingBox
import kotlinx.parcelize.Parcelize

@Entity(
    tableName = "detection_items",
    foreignKeys = [
        ForeignKey(
            entity = Inspection::class,
            parentColumns = ["id"],
            childColumns = ["inspectionId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
@Parcelize
data class DetectionItem(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val inspectionId: Long,
    val frameId: String,
    val type: DetectionType,
    @Embedded val boundingBox: BoundingBox,
    val confidence: Float,
    val status: DetectionStatus,
    val measurementMm: Float? = null,
    val timestamp: Long,
    val linkedQrCode: String? = null,
    val notes: String = "",
) : Parcelable


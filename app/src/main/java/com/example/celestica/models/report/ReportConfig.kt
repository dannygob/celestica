package com.example.celestica.models.report

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

/**
 * Define la configuración de exportación de reportes en Celestic.
 * Permite persistir preferencias como formato, filtros y metadatos.
 */
@Parcelize
@Entity(tableName = "report_config")
data class ReportConfig(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,

    @ColumnInfo(name = "report_title")
    val reportTitle: String,

    @ColumnInfo(name = "include_metadata")
    val includeMetadata: Boolean,

    @ColumnInfo(name = "include_raw_features")
    val includeRawFeatures: Boolean,

    @ColumnInfo(name = "export_format")
    val exportFormat: String, // Ej: "PDF", "CSV", "JSON"

    @ColumnInfo(name = "generation_date")
    val generationDate: String, // "YYYY-MM-DD HH:mm"

    @ColumnInfo(name = "include_images")
    val includeImages: Boolean,

    @ColumnInfo(name = "output_format")
    val outputFormat: String, // "PDF", "Word", "JSON"

    @ColumnInfo(name = "include_measurements")
    val includeMeasurements: Boolean,


    ) : Parcelable
package com.example.celestica.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.celestica.data.dao.CelesticDao
import com.example.celestica.database.converters.Converters
import com.example.celestica.models.DetectionItem
import com.example.celestica.models.Inspection
import com.example.celestica.models.calibration.CameraCalibrationData
import com.example.celestica.models.calibration.DetectedFeature
import com.example.celestica.models.report.ReportConfig

@Database(
    entities = [DetectionItem::class, DetectedFeature::class, CameraCalibrationData::class, ReportConfig::class, Inspection::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class CelesticDatabase : RoomDatabase() {
    abstract fun celesticDao(): CelesticDao

    companion object {
        @Volatile
        private var INSTANCE: CelesticDatabase? = null

        fun getDatabase(context: Context): CelesticDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CelesticDatabase::class.java,
                    "celestic_database"
                ).fallbackToDestructiveMigration(false).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
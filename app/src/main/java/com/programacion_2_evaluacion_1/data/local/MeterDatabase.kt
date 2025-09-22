package com.programacion_2_evaluacion_1.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [MeterReadingEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class MeterDatabase : RoomDatabase() {
    abstract fun meterReadingDao(): MeterReadingDao

    companion object {
        @Volatile
        private var INSTANCE: MeterDatabase? = null

        fun getDatabase(context: Context): MeterDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MeterDatabase::class.java,
                    "meter_readings.db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}

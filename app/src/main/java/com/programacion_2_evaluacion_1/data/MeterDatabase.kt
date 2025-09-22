package com.programacion_2_evaluacion_1.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

/**
 * Definición de la base de datos Room que persiste las lecturas de los medidores.
 */
@Database(entities = [MeterReading::class], version = 1, exportSchema = false)
@TypeConverters(MeterConverters::class)
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
                    "meter_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}

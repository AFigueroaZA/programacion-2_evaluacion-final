package com.programacion_2_evaluacion_1.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

/**
 * DAO que define las operaciones soportadas sobre la tabla de lecturas.
 */
@Dao
interface MeterReadingDao {
    @Query("SELECT * FROM meter_readings ORDER BY date DESC, id DESC")
    fun getAllReadings(): Flow<List<MeterReading>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(reading: MeterReading)
}

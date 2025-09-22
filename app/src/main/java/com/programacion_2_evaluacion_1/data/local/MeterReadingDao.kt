package com.programacion_2_evaluacion_1.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MeterReadingDao {
    @Query("SELECT * FROM meter_readings ORDER BY reading_date DESC, id DESC")
    fun getReadings(): Flow<List<MeterReadingEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(reading: MeterReadingEntity)
}

package com.programacion_2_evaluacion_1.data

import com.programacion_2_evaluacion_1.data.local.MeterReadingDao
import com.programacion_2_evaluacion_1.data.local.MeterReadingEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MeterRepository(private val meterReadingDao: MeterReadingDao) {
    val readings: Flow<List<MeterReading>> =
        meterReadingDao.getReadings().map { entities ->
            entities.map { it.toModel() }
        }

    suspend fun addReading(reading: MeterReading) {
        meterReadingDao.insert(reading.toEntity())
    }
}

private fun MeterReadingEntity.toModel(): MeterReading =
    MeterReading(id = id, meterValue = meterValue, readingDate = readingDate, type = type)

private fun MeterReading.toEntity(): MeterReadingEntity =
    MeterReadingEntity(id = id, meterValue = meterValue, readingDate = readingDate, type = type)

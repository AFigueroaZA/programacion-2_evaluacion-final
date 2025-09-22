package com.programacion_2_evaluacion_1.data

import kotlinx.coroutines.flow.Flow

/**
 * Capa de repositorio que aísla al resto de la aplicación del acceso directo a Room.
 */
class MeterRepository(private val dao: MeterReadingDao) {
    val readings: Flow<List<MeterReading>> = dao.getAllReadings()

    suspend fun addReading(reading: MeterReading) {
        dao.insert(reading)
    }
}

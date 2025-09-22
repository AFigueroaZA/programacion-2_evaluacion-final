package com.programacion_2_evaluacion_1.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

/**
 * Entidad que representa una lectura de un medidor almacenada en la base de datos local.
 */
@Entity(tableName = "meter_readings")
data class MeterReading(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val type: MeterType,
    val value: Double,
    val date: LocalDate
)

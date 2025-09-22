package com.programacion_2_evaluacion_1.data

import java.time.LocalDate

data class MeterReading(
    val id: Int = 0,
    val meterValue: Int,
    val readingDate: LocalDate,
    val type: MeterType
)

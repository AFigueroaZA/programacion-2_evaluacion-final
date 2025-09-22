package com.programacion_2_evaluacion_1.data

import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.format.DateTimeFormatter

/**
 * Conversores necesarios para que Room pueda persistir tipos personalizados.
 */
class MeterConverters {
    private val formatter = DateTimeFormatter.ISO_LOCAL_DATE

    @TypeConverter
    fun fromType(value: MeterType): String = value.name

    @TypeConverter
    fun toType(value: String): MeterType = MeterType.valueOf(value)

    @TypeConverter
    fun fromDate(value: LocalDate): String = value.format(formatter)

    @TypeConverter
    fun toDate(value: String): LocalDate = LocalDate.parse(value, formatter)
}

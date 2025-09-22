package com.programacion_2_evaluacion_1.data.local

import androidx.room.TypeConverter
import com.programacion_2_evaluacion_1.data.MeterType
import java.time.LocalDate

class Converters {
    @TypeConverter
    fun fromLocalDate(date: LocalDate): Long = date.toEpochDay()

    @TypeConverter
    fun toLocalDate(epochDay: Long): LocalDate = LocalDate.ofEpochDay(epochDay)

    @TypeConverter
    fun fromMeterType(type: MeterType): String = type.name

    @TypeConverter
    fun toMeterType(value: String): MeterType = MeterType.valueOf(value)
}

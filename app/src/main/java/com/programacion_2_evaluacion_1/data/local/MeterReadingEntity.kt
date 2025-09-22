package com.programacion_2_evaluacion_1.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.programacion_2_evaluacion_1.data.MeterType
import java.time.LocalDate

@Entity(tableName = "meter_readings")
data class MeterReadingEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "meter_value") val meterValue: Int,
    @ColumnInfo(name = "reading_date") val readingDate: LocalDate,
    @ColumnInfo(name = "type") val type: MeterType
)

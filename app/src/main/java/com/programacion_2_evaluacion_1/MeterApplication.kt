package com.programacion_2_evaluacion_1

import android.app.Application
import com.programacion_2_evaluacion_1.data.MeterRepository
import com.programacion_2_evaluacion_1.data.local.MeterDatabase

class MeterApplication : Application() {
    val database: MeterDatabase by lazy { MeterDatabase.getDatabase(this) }
    val repository: MeterRepository by lazy { MeterRepository(database.meterReadingDao()) }
}

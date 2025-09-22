package com.programacion_2_evaluacion_1.data

import androidx.annotation.StringRes
import com.programacion_2_evaluacion_1.R

enum class MeterType(@StringRes val labelRes: Int) {
    WATER(R.string.meter_type_water),
    ELECTRICITY(R.string.meter_type_electricity),
    GAS(R.string.meter_type_gas)
}

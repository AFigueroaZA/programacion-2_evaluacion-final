package com.programacion_2_evaluacion_1.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.programacion_2_evaluacion_1.R

/**
 * Enumeración que representa los tipos de medidores soportados por la aplicación.
 */
enum class MeterType(
    @StringRes val labelRes: Int,
    @DrawableRes val iconRes: Int
) {
    WATER(R.string.type_water, R.drawable.ic_water),
    ELECTRICITY(R.string.type_electricity, R.drawable.ic_electricity),
    GAS(R.string.type_gas, R.drawable.ic_gas)
}

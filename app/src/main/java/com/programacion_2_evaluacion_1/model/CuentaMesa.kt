package com.programacion_2_evaluacion_1.model

class CuentaMesa(val mesa: Int) {
    val items: MutableList<ItemMesa> = mutableListOf()
    var aceptaPropina: Boolean = true

    fun agregarItem(itemMenu: ItemMenu, cantidad: Int) {
        items.add(ItemMesa(itemMenu, cantidad))
    }

    fun agregarItem(itemMesa: ItemMesa) {
        items.add(itemMesa)
    }

    fun calcularTotalSinPropina(): Int = items.sumOf { it.calcularSubtotal() }

    fun calcularPropina(): Int = if (aceptaPropina) (calcularTotalSinPropina() * 0.1).toInt() else 0

    fun calcularTotalConPropina(): Int = calcularTotalSinPropina() + calcularPropina()
}

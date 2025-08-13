package com.programacion_2_evaluacion_1.model

// Administra los ítems y cálculos de una cuenta por mesa
class CuentaMesa(val mesa: Int) {
    val items: MutableList<ItemMesa> = mutableListOf()
    var aceptaPropina: Boolean = true

    // Agrega un ítem del menú indicando su cantidad
    fun agregarItem(itemMenu: ItemMenu, cantidad: Int) {
        items.add(ItemMesa(itemMenu, cantidad))
    }

    // Agrega un ítem de mesa ya existente
    fun agregarItem(itemMesa: ItemMesa) {
        items.add(itemMesa)
    }

    // Calcula la suma de todos los ítems sin considerar propina
    fun calcularTotalSinPropina(): Int = items.sumOf { it.calcularSubtotal() }

    // Determina el monto de propina si la mesa la acepta
    fun calcularPropina(): Int = if (aceptaPropina) (calcularTotalSinPropina() * 0.1).toInt() else 0

    // Obtiene el total a pagar incluyendo la propina
    fun calcularTotalConPropina(): Int = calcularTotalSinPropina() + calcularPropina()
}

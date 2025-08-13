package com.programacion_2_evaluacion_1.model

class ItemMesa(val itemMenu: ItemMenu, var cantidad: Int) {
    fun calcularSubtotal(): Int = itemMenu.precio * cantidad
}

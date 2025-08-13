package com.programacion_2_evaluacion_1.model

// Relaciona un ítem del menú con la cantidad solicitada en la mesa
class ItemMesa(val itemMenu: ItemMenu, var cantidad: Int) {
    // Calcula el subtotal del ítem según su cantidad
    fun calcularSubtotal(): Int = itemMenu.precio * cantidad
}

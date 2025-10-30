package com.pasteleria.ui.boleta

import com.pasteleria.ui.home.Producto



data class PedidoItem(
    final public val producto: Producto,
    final public val cantidad: Int
)
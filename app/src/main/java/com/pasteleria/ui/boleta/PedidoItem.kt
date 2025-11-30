package com.pasteleria.ui.boleta
import com.pasteleria.data.model.Producto

data class PedidoItem(
    val producto: Producto,
    val cantidad: Int
)
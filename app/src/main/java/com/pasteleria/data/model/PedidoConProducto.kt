package com.pasteleria.data.model

import androidx.room.Embedded
import androidx.room.Relation

data class PedidoConProducto(
    @Embedded val pedido: PedidoEntity,

    @Relation(
        parentColumn = "productoId",
        entityColumn = "id"
    )
    val producto: Producto
)
package com.pasteleria.data.model

import androidx.room.Entity
import androidx.room.ForeignKey

// Definimos la clave compuesta para que sea único por usuario y producto
@Entity(
    tableName = "pedidos_carrito",
    primaryKeys = ["username", "productoId"],
    foreignKeys = [
        ForeignKey(
            entity = Producto::class,
            parentColumns = ["id"],
            childColumns = ["productoId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class PedidoEntity(
    val username: String,
    val productoId: Int,
    val cantidad: Int
)
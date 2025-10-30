package com.pasteleria.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "productos") // Nombre de la tabla en la BD
data class Producto(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nombre: String,
    val precio: Int,
    val imagenResId: Int // Room puede guardar el ID del recurso (R.drawable.cupcake)
)
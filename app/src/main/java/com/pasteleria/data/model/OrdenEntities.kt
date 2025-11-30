package com.pasteleria.data.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.Relation

// 1. La Cabecera de la Boleta (Ej: Boleta #10, Fecha: 12/12/2023, Total: $5000)
@Entity(tableName = "ordenes")
data class OrdenEntity(
    @PrimaryKey(autoGenerate = true) val ordenId: Long = 0,
    val username: String,
    val fecha: Long,
    val total: Double
)

@Entity(
    tableName = "orden_detalles",
    foreignKeys = [
        ForeignKey(
            entity = OrdenEntity::class,
            parentColumns = ["ordenId"],
            childColumns = ["ordenIdOwner"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class OrdenDetalleEntity(
    @PrimaryKey(autoGenerate = true) val detalleId: Long = 0,
    val ordenIdOwner: Long,
    val nombreProducto: String,
    val cantidad: Int,
    val precioUnitario: Int
)

// 3. Clase auxiliar para leer la Boleta completa con sus productos
data class OrdenConDetalles(
    @Embedded val orden: OrdenEntity,
    @Relation(
        parentColumn = "ordenId",
        entityColumn = "ordenIdOwner"
    )
    val detalles: List<OrdenDetalleEntity>
)
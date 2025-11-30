package com.pasteleria.data.dao

import androidx.room.*
import com.pasteleria.data.model.OrdenConDetalles
import com.pasteleria.data.model.OrdenDetalleEntity
import com.pasteleria.data.model.OrdenEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface OrdenDao {
    // Insertar la cabecera y devolver el ID generado
    @Insert
    suspend fun insertarOrden(orden: OrdenEntity): Long

    // Insertar los productos de esa orden
    @Insert
    suspend fun insertarDetalles(detalles: List<OrdenDetalleEntity>)

    // Obtener todas las boletas de un usuario con sus detalles
    @Transaction
    @Query("SELECT * FROM ordenes WHERE username = :username ORDER BY fecha DESC")
    fun obtenerHistorial(username: String): Flow<List<OrdenConDetalles>>
}
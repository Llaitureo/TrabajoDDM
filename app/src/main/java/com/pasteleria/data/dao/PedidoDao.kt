package com.pasteleria.data.dao

import androidx.room.*
import com.pasteleria.data.model.PedidoEntity
import com.pasteleria.data.model.PedidoConProducto
import kotlinx.coroutines.flow.Flow

@Dao
interface PedidoDao {

    @Transaction
    @Query("SELECT * FROM pedidos_carrito WHERE username = :username")
    fun obtenerCarrito(username: String): Flow<List<PedidoConProducto>>

    // Buscar si ya existe un producto en el carrito para sumar cantidad
    @Query("SELECT * FROM pedidos_carrito WHERE username = :username AND productoId = :productoId LIMIT 1")
    suspend fun obtenerPedidoEspecifico(username: String, productoId: Int): PedidoEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarPedido(pedido: PedidoEntity)

    @Delete
    suspend fun eliminarPedido(pedido: PedidoEntity)

    @Query("DELETE FROM pedidos_carrito WHERE username = :username")
    suspend fun vaciarCarrito(username: String)
}
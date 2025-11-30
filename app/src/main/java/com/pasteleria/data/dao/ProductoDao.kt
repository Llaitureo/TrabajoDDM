package com.pasteleria.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pasteleria.data.model.Producto
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductoDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(productos: List<Producto>)
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(producto: Producto): Long
    @Query("SELECT * FROM productos WHERE nombre = :nombre LIMIT 1")
    suspend fun obtenerPorNombre(nombre: String): Producto?
    @Query("SELECT * FROM productos")
    fun getAllProductos(): Flow<List<Producto>>
}
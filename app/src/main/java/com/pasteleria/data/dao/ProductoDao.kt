package com.pasteleria.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pasteleria.data.model.Producto
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductoDao {

    // Usamos OnConflictStrategy.IGNORE para no insertar productos duplicados
    // si intentamos pre-poblar la base de datos varias veces.
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(productos: List<Producto>)

    @Query("SELECT * FROM productos")
    fun getAllProductos(): Flow<List<Producto>>
}
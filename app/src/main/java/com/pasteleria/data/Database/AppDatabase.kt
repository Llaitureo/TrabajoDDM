
package com.pasteleria.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.pasteleria.data.dao.OrdenDao
import com.pasteleria.data.dao.UserDao
import com.pasteleria.data.model.User
import com.pasteleria.data.dao.ProductoDao
import com.pasteleria.data.model.Producto
import com.pasteleria.data.dao.PedidoDao
import com.pasteleria.data.model.PedidoEntity
import com.pasteleria.data.model.OrdenEntity
import com.pasteleria.data.model.OrdenDetalleEntity

@Database(
    entities = [
        User::class,
        Producto::class,
        PedidoEntity::class,       // Carrito
        OrdenEntity::class,        // Historial Cabecera
        OrdenDetalleEntity::class  // Historial Detalles
    ],
    version = 5, // Asegúrate de subir la versión (ej: a 5)
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun productoDao(): ProductoDao

    abstract fun pedidoDao(): PedidoDao
    abstract fun ordenDao(): OrdenDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "pasteleria_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
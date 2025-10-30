package com.pasteleria.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.pasteleria.data.dao.UserDao
import com.pasteleria.data.model.User
// 1. Importa el modelo y el DAO de Producto
import com.pasteleria.data.dao.ProductoDao
import com.pasteleria.data.model.Producto

// 2. Añade Producto::class a 'entities' y sube la versión de 1 a 2
@Database(entities = [User::class, Producto::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    // 3. Añade el nuevo DAO
    abstract fun productoDao(): ProductoDao

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
                    // 4. AÑADE ESTO: Como cambiaste la versión,
                    // necesitas decirle a Room cómo migrar.
                    // Esta es la forma más fácil: borra y recrea la BD.
                    // (Pierdes los datos de usuarios si ya tenías)
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
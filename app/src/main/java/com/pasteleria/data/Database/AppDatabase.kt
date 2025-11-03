package com.pasteleria.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.pasteleria.data.dao.UserDao
import com.pasteleria.data.model.User
import com.pasteleria.data.dao.ProductoDao
import com.pasteleria.data.model.Producto

// El error ocurre porque el modelo User cambió, pero la versión de la BD no.
// Subiendo la versión de 2 a 3 para que Room actualice el esquema.
@Database(entities = [User::class, Producto::class], version = 3, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
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
                    // La migración destructiva borrará los datos y recreará la BD
                    // con el nuevo esquema de User.
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
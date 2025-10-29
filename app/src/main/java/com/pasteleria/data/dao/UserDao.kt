package com.pasteleria.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pasteleria.data.model.User
import kotlinx.coroutines.flow.Flow // Opcional si quieres observar cambios

@Dao
interface UserDao {

    // Inserta un nuevo usuario. Si ya existe, lo reemplaza (podrías cambiar a IGNORE)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    // Busca un usuario por su nombre de usuario
    @Query("SELECT * FROM users WHERE username = :username LIMIT 1")
    suspend fun getUserByUsername(username: String): User? // Puede ser null si no existe

    // Opcional: Obtener todos los usuarios (para debug o listas)
    @Query("SELECT * FROM users")
    fun getAllUsers(): Flow<List<User>> // Flow para observar cambios
}
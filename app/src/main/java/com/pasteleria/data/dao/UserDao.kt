package com.pasteleria.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pasteleria.data.model.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    // Inserta un nuevo usuario. Si ya existe, lo reemplaza (podrías cambiar a IGNORE)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    @Query("SELECT * FROM users WHERE username = :username LIMIT 1")
    suspend fun getUserByUsername(username: String): User?

    //Obtiene a todos los usuarios
    @Query("SELECT * FROM users")
    fun getAllUsers(): Flow<List<User>>
}
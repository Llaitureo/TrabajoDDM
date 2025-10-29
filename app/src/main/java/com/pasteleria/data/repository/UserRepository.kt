package com.pasteleria.data.repository

import com.pasteleria.data.dao.UserDao
import com.pasteleria.data.model.User
import kotlinx.coroutines.flow.Flow // Opcional

class UserRepository(private val userDao: UserDao) {

    // Inserta un usuario (usando coroutines)
    suspend fun insertUser(user: User) {
        // Aquí podrías añadir lógica de hashing de contraseña antes de insertar
        userDao.insertUser(user)
    }

    // Busca un usuario por nombre (usando coroutines)
    suspend fun findUserByUsername(username: String): User? {
        return userDao.getUserByUsername(username)
    }

    // Opcional: Obtener todos los usuarios como un Flow
    val allUsers: Flow<List<User>> = userDao.getAllUsers()
}
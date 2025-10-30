package com.pasteleria.data.repository

import com.pasteleria.data.dao.UserDao
import com.pasteleria.data.model.User
import kotlinx.coroutines.flow.Flow

class UserRepository(private val userDao: UserDao) {

    suspend fun insertUser(user: User) {
        userDao.insertUser(user)
    }
    suspend fun findUserByUsername(username: String): User? {
        return userDao.getUserByUsername(username)
    }

    val allUsers: Flow<List<User>> = userDao.getAllUsers()
}
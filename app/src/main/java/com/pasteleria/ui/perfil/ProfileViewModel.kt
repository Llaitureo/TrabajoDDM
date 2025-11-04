package com.pasteleria.ui.perfil

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.pasteleria.data.database.AppDatabase
import com.pasteleria.data.model.User
import com.pasteleria.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(application: Application) : AndroidViewModel(application) {

    private val userRepository: UserRepository
    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user.asStateFlow()

    init {
        val userDao = AppDatabase.getDatabase(application).userDao()
        userRepository = UserRepository(userDao)
    }

    fun loadUser(username: String) {
        viewModelScope.launch {
            val user = if (username.lowercase() == "admin") {
                // Usuario admin hardcodeado
                User(
                    id = 1,
                    username = username,
                    passwordHash = "",
                    birthDate = "15/03/1970",
                    discountCode = null,
                    hasFiftyPercentDiscount = true,
                    hasTenPercentDiscount = false,
                    hasFreeCakeOnBirthday = true
                )
            } else {
                // Buscar usuario real en la base de datos
                userRepository.findUserByUsername(username)
            }
            _user.value = user
        }
    }
}
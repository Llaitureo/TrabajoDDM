package com.pasteleria.ui.perfil

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pasteleria.data.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {

    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user.asStateFlow()

    fun loadUser(username: String) {
        viewModelScope.launch {
            val user = when (username.lowercase()) {
                "admin" -> User(
                    id = 1,
                    username = username,
                    passwordHash = "",
                    birthDate = "15/03/1970",
                    discountCode = null,
                    hasFiftyPercentDiscount = true,
                    hasTenPercentDiscount = false,
                    hasFreeCakeOnBirthday = true
                )
                else -> User(
                    id = 1,
                    username = username,
                    passwordHash = "",
                    birthDate = "22/08/1995",
                    discountCode = "FELICES50",
                    hasFiftyPercentDiscount = false,
                    hasTenPercentDiscount = true,
                    hasFreeCakeOnBirthday = false
                )
            }
            _user.value = user
        }
    }
}
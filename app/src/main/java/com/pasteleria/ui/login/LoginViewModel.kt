package com.pasteleria.ui.login

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.pasteleria.data.database.AppDatabase
import com.pasteleria.data.model.Credential
import com.pasteleria.data.repository.UserRepository
import kotlinx.coroutines.launch

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val userRepository: UserRepository
    private val adminCredential = Credential.admin

    var uiState by mutableStateOf(LoginUiState())
        private set

    init {
        val userDao = AppDatabase.getDatabase(application).userDao()
        userRepository = UserRepository(userDao)
    }

    fun onUsernameChange(value: String) {
        uiState = uiState.copy(username = value.trim(), error = null, usernameError = null)
    }

    fun onPasswordChange(value: String) {
        uiState = uiState.copy(password = value, error = null, passwordError = null)
    }

    fun submit(onSuccess: (String) -> Unit) {
        if (uiState.username.isBlank() || uiState.password.isBlank()) {
            val usernameError = if (uiState.username.isBlank()) "Rellene este campo" else null
            val passwordError = if (uiState.password.isBlank()) "Rellene este campo" else null
            uiState = uiState.copy(usernameError = usernameError, passwordError = passwordError)
            return
        }

        uiState = uiState.copy(isLoading = true, error = null, usernameError = null, passwordError = null)
        val currentUsername = uiState.username
        val currentPassword = uiState.password

        viewModelScope.launch {
            try {
                if (currentUsername == adminCredential.username && currentPassword == adminCredential.password) {
                    uiState = uiState.copy(isLoading = false)
                    onSuccess(currentUsername)
                } else {
                    val userFromDb = userRepository.findUserByUsername(currentUsername)

                    if (userFromDb != null && userFromDb.passwordHash == currentPassword) {
                        uiState = uiState.copy(isLoading = false)
                        onSuccess(currentUsername)
                    } else {
                        uiState = uiState.copy(isLoading = false, error = "Credenciales inválidas.")
                    }
                }
            } catch (e: Exception) {
                uiState = uiState.copy(isLoading = false, error = "Error de base de datos: ${e.message}")
            }
        }
    }
}

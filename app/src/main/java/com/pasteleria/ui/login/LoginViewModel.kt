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

    // Repositorio para consultar usuarios de la BD
    private val userRepository: UserRepository
    private val adminCredential = Credential.admin

    var uiState by mutableStateOf(LoginUiState())
        private set

    init {
        // Inicialización del repositorio
        val userDao = AppDatabase.getDatabase(application).userDao()
        userRepository = UserRepository(userDao)
    }

    fun onUsernameChange(value: String) {
        uiState = uiState.copy(username = value.trim(), error = null)
    }

    fun onPasswordChange(value: String) {
        uiState = uiState.copy(password = value, error = null)
    }

    fun submit(onSuccess: (String) -> Unit) {
        uiState = uiState.copy(isLoading = true, error = null)
        val currentUsername = uiState.username
        val currentPassword = uiState.password

        viewModelScope.launch {
            try {
                    //Admin
                if (currentUsername == adminCredential.username && currentPassword == adminCredential.password) {
                    uiState = uiState.copy(isLoading = false)
                    onSuccess(currentUsername)
                } else {
                    //User de bdd
                    val userFromDb = userRepository.findUserByUsername(currentUsername)

                    if (userFromDb != null && userFromDb.passwordHash == currentPassword) {
                        uiState = uiState.copy(isLoading = false)
                        onSuccess(currentUsername)
                    } else {
                        //Usuario no encontrado o contraseña incorrecta
                        uiState = uiState.copy(isLoading = false, error = "Credenciales inválidas.")
                    }
                }
            } catch (e: Exception) {
                uiState = uiState.copy(isLoading = false, error = "Error de base de datos: ${e.message}")
            }
        }
    }
}
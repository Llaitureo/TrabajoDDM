package com.pasteleria.ui.login

import android.app.Application // Necesitamos Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel // Cambiamos a AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.pasteleria.data.database.AppDatabase // Importamos la BD
import com.pasteleria.data.model.Credential
import com.pasteleria.data.repository.UserRepository // Importamos el repo de usuario
import kotlinx.coroutines.launch

// El LoginUiState sigue igual
// data class LoginUiState(...)

class LoginViewModel(application: Application) : AndroidViewModel(application) { // Hereda de AndroidViewModel

    // Repositorio para consultar usuarios de la BD
    private val userRepository: UserRepository
    // Mantenemos la referencia al admin original
    private val adminCredential = Credential.admin

    var uiState by mutableStateOf(LoginUiState())
        private set

    init {
        // Inicializamos el repositorio
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
                // 1. Comprueba si es el admin hardcodeado
                if (currentUsername == adminCredential.username && currentPassword == adminCredential.password) {
                    uiState = uiState.copy(isLoading = false)
                    onSuccess(currentUsername)
                } else {
                    // 2. Si no es admin, busca en la base de datos
                    val userFromDb = userRepository.findUserByUsername(currentUsername)

                    if (userFromDb != null && userFromDb.passwordHash == currentPassword) {
                        // ¡Usuario encontrado y contraseña correcta! (Recuerda usar hash en real)
                        uiState = uiState.copy(isLoading = false)
                        onSuccess(currentUsername)
                    } else {
                        // Usuario no encontrado o contraseña incorrecta
                        uiState = uiState.copy(isLoading = false, error = "Credenciales inválidas.")
                    }
                }
            } catch (e: Exception) {
                uiState = uiState.copy(isLoading = false, error = "Error de base de datos: ${e.message}")
            }
        }
    }
}
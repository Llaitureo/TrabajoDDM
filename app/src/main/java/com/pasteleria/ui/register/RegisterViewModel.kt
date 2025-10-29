package com.pasteleria.ui.register // Asegúrate que la ruta sea correcta

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.pasteleria.data.database.AppDatabase
import com.pasteleria.data.model.User
import com.pasteleria.data.repository.UserRepository
import kotlinx.coroutines.launch

// Estado para la UI de registro
data class RegisterUiState(
    val username: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val registrationSuccess: Boolean = false
)

// Usamos AndroidViewModel para tener acceso al Context para la BD
class RegisterViewModel(application: Application) : AndroidViewModel(application) {

    private val userRepository: UserRepository
    var uiState by mutableStateOf(RegisterUiState())
        private set

    init {
        // Inicializamos el repositorio obteniendo el DAO desde la BD
        val userDao = AppDatabase.getDatabase(application).userDao()
        userRepository = UserRepository(userDao)
    }

    // Funciones para actualizar el estado desde la UI
    fun onUsernameChange(value: String) {
        uiState = uiState.copy(username = value.trim(), error = null)
    }
    fun onPasswordChange(value: String) {
        uiState = uiState.copy(password = value, error = null)
        validatePasswordsMatch()
    }
    fun onConfirmPasswordChange(value: String) {
        uiState = uiState.copy(confirmPassword = value, error = null)
        validatePasswordsMatch()
    }

    private fun validatePasswordsMatch() {
        if (uiState.password.isNotEmpty() && uiState.confirmPassword.isNotEmpty() &&
            uiState.password != uiState.confirmPassword) {
            uiState = uiState.copy(error = "Las contraseñas no coinciden")
        } else {
            uiState = uiState.copy(error = null) // Limpia el error si coinciden o están vacíos
        }
    }

    fun registerUser() {
        if (uiState.username.isBlank() || uiState.password.isBlank()) {
            uiState = uiState.copy(error = "Usuario y contraseña no pueden estar vacíos")
            return
        }
        if (uiState.password != uiState.confirmPassword) {
            uiState = uiState.copy(error = "Las contraseñas no coinciden")
            return
        }
        // Evita registrar "admin"
        if (uiState.username.equals("admin", ignoreCase = true)) {
            uiState = uiState.copy(error = "El nombre de usuario 'admin' está reservado.")
            return
        }


        uiState = uiState.copy(isLoading = true, error = null)

        viewModelScope.launch {
            try {
                // 1. Verifica si el usuario ya existe
                val existingUser = userRepository.findUserByUsername(uiState.username)
                if (existingUser != null) {
                    uiState = uiState.copy(isLoading = false, error = "El nombre de usuario ya existe")
                } else {
                    // 2. Crea el nuevo usuario (¡Recuerda hashear la contraseña en una app real!)
                    val newUser = User(username = uiState.username, passwordHash = uiState.password)
                    userRepository.insertUser(newUser)
                    uiState = uiState.copy(isLoading = false, registrationSuccess = true)
                }
            } catch (e: Exception) {
                uiState = uiState.copy(isLoading = false, error = "Error al registrar: ${e.message}")
            }
        }
    }

    // Para resetear el estado después de un registro exitoso si es necesario
    fun registrationHandled() {
        uiState = uiState.copy(registrationSuccess = false)
    }
}
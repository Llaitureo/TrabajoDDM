package com.pasteleria.ui.register

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
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

data class RegisterUiState(
    val username: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val birthDate: String = "",      // Nuevo campo
    val discountCode: String = "",  // Nuevo campo
    val isLoading: Boolean = false,
    val error: String? = null,
    val registrationSuccess: Boolean = false
)

class RegisterViewModel(application: Application) : AndroidViewModel(application) {

    private val userRepository: UserRepository
    var uiState by mutableStateOf(RegisterUiState())
        private set

    init {
        val userDao = AppDatabase.getDatabase(application).userDao()
        userRepository = UserRepository(userDao)
    }

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

    // Nuevas funciones para los campos añadidos
    fun onBirthDateChange(value: String) {
        uiState = uiState.copy(birthDate = value, error = null)
    }

    fun onDiscountCodeChange(value: String) {
        uiState = uiState.copy(discountCode = value.trim(), error = null)
    }

    private fun validatePasswordsMatch() {
        if (uiState.password.isNotEmpty() && uiState.confirmPassword.isNotEmpty() &&
            uiState.password != uiState.confirmPassword
        ) {
            uiState = uiState.copy(error = "Las contraseñas no coinciden")
        } else {
            uiState = uiState.copy(error = null)
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
        if (uiState.username.equals("admin", ignoreCase = true)) {
            uiState = uiState.copy(error = "El nombre de usuario 'admin' está reservado.")
            return
        }

        // Validación de la fecha de nacimiento y edad
        var isOver50 = false
        if (uiState.birthDate.isNotBlank()) {
            try {
                val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
                val birthDate = LocalDate.parse(uiState.birthDate, formatter)
                val age = Period.between(birthDate, LocalDate.now()).years
                if (age >= 50) {
                    isOver50 = true
                }
            } catch (e: DateTimeParseException) {
                uiState = uiState.copy(error = "Formato de fecha inválido. Usa DD/MM/AAAA.")
                return
            }
        }

        // Validación del correo de Duoc
        val isDuocStudent = uiState.username.endsWith("@duoc.cl", ignoreCase = true)
        var hasFreeCake = false
        if (isDuocStudent && uiState.birthDate.isNotBlank()) {
            try {
                val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
                val birthDate = LocalDate.parse(uiState.birthDate, formatter)
                val today = LocalDate.now()
                if (birthDate.month == today.month && birthDate.dayOfMonth == today.dayOfMonth) {
                    hasFreeCake = true
                }
            } catch (e: DateTimeParseException) {
                // El error de formato ya se maneja arriba
            }
        }


        // Validación del código de descuento
        val hasTenPercentDiscount = uiState.discountCode.equals("FELICES50", ignoreCase = true)


        uiState = uiState.copy(isLoading = true, error = null)

        viewModelScope.launch {
            try {
                val existingUser = userRepository.findUserByUsername(uiState.username)
                if (existingUser != null) {
                    uiState =
                        uiState.copy(isLoading = false, error = "El nombre de usuario ya existe")
                } else {
                    val newUser = User(
                        username = uiState.username,
                        passwordHash = uiState.password,
                        birthDate = uiState.birthDate,
                        discountCode = uiState.discountCode,
                        hasFiftyPercentDiscount = isOver50,
                        hasTenPercentDiscount = hasTenPercentDiscount,
                        hasFreeCakeOnBirthday = hasFreeCake
                    )
                    userRepository.insertUser(newUser)
                    uiState = uiState.copy(isLoading = false, registrationSuccess = true)
                }
            } catch (e: Exception) {
                uiState =
                    uiState.copy(isLoading = false, error = "Error al registrar: ${e.message}")
            }
        }
    }

    fun registrationHandled() {
        uiState = uiState.copy(registrationSuccess = false)
    }
}

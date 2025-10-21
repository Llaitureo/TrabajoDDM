package com.pasteleria.ui.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.pasteleria.data.repository.AuthRepository

class LoginViewModel (
    private val repo: AuthRepository = AuthRepository()
): ViewModel() {
    var uiState by mutableStateOf(LoginUiState())
        private set


    fun onUsernameChange(value: String){
        uiState = uiState.copy(username = value, error = null)
    }//termino fun

    fun onPasswordChange(value: String){
        uiState = uiState.copy(password = value, error = null)
    }//termino fun

    fun submit(onSuccess:(String) -> Unit){
        uiState = uiState.copy(isLoading = true, error = null)
        val oK = repo.login(uiState.username.trim(), uiState.password.trim())

        //resultado
        uiState = uiState.copy(isLoading = false)

        if(oK) onSuccess(uiState.username.trim())
        else uiState = uiState.copy(error = "Error de vavidación.")

    }//termino fun
}//termino view
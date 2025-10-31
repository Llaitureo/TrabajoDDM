package com.pasteleria.ui.login

data class LoginUiState (
    val username:String = "",
    val password:String = "",
    val isLoading:Boolean = false,
    val error:String? = null,
    val usernameError: String? = null,
    val passwordError: String? = null
)
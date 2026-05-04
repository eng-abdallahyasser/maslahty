package com.abdallahyasser.maslahty.presentation.auth.login

data class LoginState(
    val nationalId: String = "",
    val phoneNumber: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val success: Boolean = false
)

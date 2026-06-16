package com.abdallahyasser.maslahty.presentation.auth.login

data class LoginState(
    val nationalIdOrEmail: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val success: Boolean = false,
    val isEmailNotVerified: Boolean = false,
    val isResendingVerification: Boolean = false,
    val resendVerificationSuccess: Boolean = false,
    val resendVerificationError: String? = null
)

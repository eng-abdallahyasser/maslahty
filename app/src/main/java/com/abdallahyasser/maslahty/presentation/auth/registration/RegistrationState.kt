package com.abdallahyasser.maslahty.presentation.auth.registration

data class RegistrationState(
    val fullName: String = "",
    val nationalId: String = "",
    val email: String = "",
    val phoneNumber: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val success: Boolean = false
)

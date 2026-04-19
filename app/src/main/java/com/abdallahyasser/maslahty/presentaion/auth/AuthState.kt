package com.abdallahyasser.maslahty.presentaion.auth

data class AuthState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val success: Boolean = false,
    val carName: String = "",
    val nationalId: String = "",
    val otpValue: String="",
    val timeRemaining: String = "",
    val fullName : String= "",
    val phoneNumber: String= "",
    val email: String= "",
    )

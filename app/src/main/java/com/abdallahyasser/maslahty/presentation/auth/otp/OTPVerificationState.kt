package com.abdallahyasser.maslahty.presentation.auth.otp

data class OTPVerificationState(
    val otpValue: String = "",
    val phoneNumber: String = "",
    val timeRemaining: String = "02:00",
    val isLoading: Boolean = false,
    val error: String? = null,
    val success: Boolean = false
)

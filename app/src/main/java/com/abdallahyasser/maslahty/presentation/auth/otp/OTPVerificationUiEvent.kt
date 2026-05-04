package com.abdallahyasser.maslahty.presentation.auth.otp

sealed class OTPVerificationUiEvent {
    data class ShowSnackbar(val message: String) : OTPVerificationUiEvent()
    object NavigateToHome : OTPVerificationUiEvent()
}

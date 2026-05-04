package com.abdallahyasser.maslahty.presentation.auth.login

sealed class LoginUiEvent {
    data class ShowSnackbar(val message: String) : LoginUiEvent()
    object NavigateToVerifyOTP : LoginUiEvent()
}

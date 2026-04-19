package com.abdallahyasser.maslahty.presentation.auth

sealed class AuthUiEvent {
    object NavigateToHome : AuthUiEvent()
    data class ShowSnackbar(val message: String) : AuthUiEvent()
}
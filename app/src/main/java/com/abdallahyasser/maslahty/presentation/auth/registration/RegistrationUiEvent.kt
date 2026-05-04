package com.abdallahyasser.maslahty.presentation.auth.registration

sealed class RegistrationUiEvent {
    data class ShowSnackbar(val message: String) : RegistrationUiEvent()
    object NavigateToVerifyOTP : RegistrationUiEvent()
}

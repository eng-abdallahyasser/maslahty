package com.abdallahyasser.maslahty.presentation.auth.registration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdallahyasser.maslahty.domain.auth.entity.User
import com.abdallahyasser.maslahty.domain.auth.useCase.RegisterUserUseCase
import com.abdallahyasser.maslahty.domain.auth.useCase.sendOtpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val registerUseCase: RegisterUserUseCase,
    private val sendOtpUseCase: sendOtpUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(RegistrationState())
    val state: StateFlow<RegistrationState> = _state.asStateFlow()

    private val _eventFlow = MutableSharedFlow<RegistrationUiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onFullNameChange(value: String) {
        _state.value = _state.value.copy(fullName = value)
    }

    fun onNationalIdChange(value: String) {
        _state.value = _state.value.copy(nationalId = value)
    }

    fun onEmailChange(value: String) {
        _state.value = _state.value.copy(email = value)
    }

    fun onPhoneNumberChange(value: String) {
        _state.value = _state.value.copy(phoneNumber = value)
    }

    fun onPasswordChange(value: String) {
        _state.value = _state.value.copy(password = value)
    }

    fun register() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)

            val user = User(
                id = "",
                fullName = _state.value.fullName,
                nationalId = _state.value.nationalId,
                email = _state.value.email,
                phoneNumber = _state.value.phoneNumber,
                password = _state.value.password
            )

            val result = registerUseCase(user)

            if (result.isSuccess) {
                val registeredUser = result.getOrNull()
                if (registeredUser != null) {
                    val otpResult = sendOtpUseCase(registeredUser.phoneNumber)
                    if (otpResult.isSuccess) {
                        _state.value = _state.value.copy(isLoading = false, success = true)
                        _eventFlow.emit(RegistrationUiEvent.NavigateToVerifyOTP)
                    } else {
                        _state.value = _state.value.copy(
                            isLoading = false,
                            error = otpResult.exceptionOrNull()?.message ?: "Failed to send OTP"
                        )
                    }
                }
            } else {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = result.exceptionOrNull()?.message ?: "Registration failed"
                )
            }
        }
    }
}

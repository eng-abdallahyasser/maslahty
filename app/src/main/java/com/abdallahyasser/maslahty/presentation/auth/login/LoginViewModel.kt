package com.abdallahyasser.maslahty.presentation.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdallahyasser.maslahty.domain.auth.useCase.LoginUseCase
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
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val sendOtpUseCase: sendOtpUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _state.asStateFlow()

    private val _eventFlow = MutableSharedFlow<LoginUiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onNationalIdOrEmailChange(nationalId: String) {
        _state.value = _state.value.copy(nationalIdOrEmail = nationalId)
    }

    fun onPasswordChange(password: String) {
        _state.value = _state.value.copy(password = password)
    }

    fun login() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)
            
            val result = loginUseCase(
                nationalIdOrEmail = _state.value.nationalIdOrEmail.trim() ,
                password = _state.value.password.trim()
            )

            if (result.isSuccess) {
                val user = result.getOrNull()
                if (user != null) {
                    // Send OTP after successful login
//                    val otpResult = sendOtpUseCase(user.phoneNumber)
//                    if (otpResult.isSuccess) {
//                        _state.value = _state.value.copy(isLoading = false, success = true)
//                        _eventFlow.emit(LoginUiEvent.NavigateToVerifyOTP)
//                    } else {
//                        _state.value = _state.value.copy(
//                            isLoading = false,
//                            error = otpResult.exceptionOrNull()?.message ?: "Failed to send OTP"
//                        )
//                    }
                    _eventFlow.emit(LoginUiEvent.NavigateToVerifyOTP)
                }
            } else {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = result.exceptionOrNull()?.message ?: "Login failed"
                )
            }
        }
    }
}

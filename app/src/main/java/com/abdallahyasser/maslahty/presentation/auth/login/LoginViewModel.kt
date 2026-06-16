package com.abdallahyasser.maslahty.presentation.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdallahyasser.maslahty.domain.auth.entity.AuthResult
import com.abdallahyasser.maslahty.domain.auth.useCase.LoginUseCase
import com.abdallahyasser.maslahty.domain.auth.useCase.ResendVerificationEmailUseCase
import com.abdallahyasser.maslahty.domain.auth.useCase.sendOtpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import com.abdallahyasser.maslahty.util.PreferenceManager
import kotlinx.coroutines.launch

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val sendOtpUseCase: sendOtpUseCase,
    private val resendVerificationEmailUseCase: ResendVerificationEmailUseCase
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
            _state.value = _state.value.copy(isLoading = true, error = null, isEmailNotVerified = false)
            
            val result = loginUseCase(
                nationalIdOrEmail = _state.value.nationalIdOrEmail.trim(),
                password = _state.value.password.trim()
            )

            when (result) {
                is AuthResult.Success -> {
                    val user = result.data
                    PreferenceManager.saveUser(user)
                    _state.value = _state.value.copy(isLoading = false, success = true)
                    _eventFlow.emit(LoginUiEvent.NavigateToVerifyOTP)
                }
                is AuthResult.EmailNotVerified -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        isEmailNotVerified = true,
                        error = "يرجى تفعيل البريد الإلكتروني أولاً قبل تسجيل الدخول"
                    )
                }
                is AuthResult.Error -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = result.message
                    )
                }
            }
        }
    }

    fun resendVerification() {
        viewModelScope.launch {
            val email = _state.value.nationalIdOrEmail.trim()
            val password = _state.value.password.trim()

            if (email.isBlank() || password.isBlank()) {
                _state.value = _state.value.copy(
                    resendVerificationError = "يرجى إدخال البريد الإلكتروني وكلمة المرور لإعادة إرسال رمز التحقق"
                )
                return@launch
            }

            _state.value = _state.value.copy(
                isResendingVerification = true,
                resendVerificationError = null,
                resendVerificationSuccess = false
            )

            val result = resendVerificationEmailUseCase(email, password)

            if (result.isSuccess) {
                _state.value = _state.value.copy(
                    isResendingVerification = false,
                    resendVerificationSuccess = true
                )
            } else {
                _state.value = _state.value.copy(
                    isResendingVerification = false,
                    resendVerificationError = result.exceptionOrNull()?.message ?: "فشل إعادة إرسال البريد"
                )
            }
        }
    }
}

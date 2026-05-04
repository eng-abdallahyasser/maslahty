package com.abdallahyasser.maslahty.presentation.auth.otp

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.abdallahyasser.maslahty.domain.auth.useCase.sendOtpUseCase
import com.abdallahyasser.maslahty.domain.auth.useCase.verifyOtpUseCase
import com.abdallahyasser.maslahty.presentation.navigation.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class OTPVerificationViewModel @Inject constructor(
    private val verifyOtpUseCase: verifyOtpUseCase,
    private val sendOtpUseCase: sendOtpUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val navArgs = savedStateHandle.toRoute<Route.OTP>()
    private val phoneNumber = navArgs.phoneNumber

    private val _state = MutableStateFlow(OTPVerificationState(phoneNumber = phoneNumber))
    val state: StateFlow<OTPVerificationState> = _state.asStateFlow()

    private val _eventFlow = MutableSharedFlow<OTPVerificationUiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onOtpValueChange(otpValue: String) {
        if (otpValue.all { it.isDigit() } && otpValue.length <= 4) {
            _state.value = _state.value.copy(otpValue = otpValue)
        }
    }

    fun resendOtp() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)
            val result = sendOtpUseCase(phoneNumber)
            if (result.isSuccess) {
                _state.value = _state.value.copy(isLoading = false, success = true)
                _eventFlow.emit(OTPVerificationUiEvent.ShowSnackbar("رمز جديد تم إرساله"))
            } else {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = result.exceptionOrNull()?.message ?: "Failed to resend OTP"
                )
            }
        }
    }

    fun verifyOTP() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)

            Log.d("OTPVerificationViewModel", "Verifying OTP: $phoneNumber, ${_state.value.otpValue}")
            
            val result = verifyOtpUseCase(phoneNumber, _state.value.otpValue)

            if (result.isSuccess) {
                _state.value = _state.value.copy(isLoading = false, success = true)
                _eventFlow.emit(OTPVerificationUiEvent.NavigateToHome)
            } else {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = result.exceptionOrNull()?.message ?: "رمز غير صحيح"
                )
            }
        }
    }
}

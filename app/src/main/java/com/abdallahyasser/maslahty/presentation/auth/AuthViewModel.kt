package com.abdallahyasser.maslahty.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdallahyasser.maslahty.domain.auth.entity.User
import com.abdallahyasser.maslahty.domain.auth.useCase.RegisterUserUseCase
import com.abdallahyasser.maslahty.domain.auth.useCase.getCurrentUserUseCase
import com.abdallahyasser.maslahty.domain.auth.useCase.isLoggedInUseCase
import com.abdallahyasser.maslahty.domain.auth.useCase.LoginUseCase
import com.abdallahyasser.maslahty.domain.auth.useCase.logoutUseCase
import com.abdallahyasser.maslahty.domain.auth.useCase.sendOtpUseCase
import com.abdallahyasser.maslahty.domain.auth.useCase.verifyOtpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
@HiltViewModel
class AuthViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val registerUseCase: RegisterUserUseCase,
    private val logoutUseCase: logoutUseCase,
    private val getCurrentUserUseCase: getCurrentUserUseCase,
    private val sendOtpUseCase: sendOtpUseCase,
    private val verifyOtpUseCase: verifyOtpUseCase,
    private val isLoggedInUseCase: isLoggedInUseCase
) : ViewModel() {

    private val _eventFlow = MutableSharedFlow<AuthUiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _authState = MutableStateFlow(AuthState())
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    // Field update functions - these properly emit new state via copy()
    fun updateNationalId(value: String) {
        _authState.value = _authState.value.copy(nationalId = value)
    }

    fun updatePhoneNumber(value: String) {
        _authState.value = _authState.value.copy(phoneNumber = value)
    }

    fun updateFullName(value: String) {
        _authState.value = _authState.value.copy(fullName = value)
    }

    fun updateEmail(value: String) {
        _authState.value = _authState.value.copy(email = value)
    }

    fun updateCarName(value: String) {
        _authState.value = _authState.value.copy(carName = value)
    }

    fun updateOtpValue(value: String) {
        _authState.value = _authState.value.copy(otpValue = value)
    }

    fun login(phoneNumber: String, password: String) {
        viewModelScope.launch {
            // Set loading state
            _authState.value = _authState.value.copy(isLoading = true, error = null)

            // Call use case
            val result = loginUseCase(phoneNumber, password)

            // Update state based on result
            if (result.isSuccess) {
                val user = result.getOrNull()
                if (user != null) {
                    _authState.value = _authState.value.copy(
                        isLoading = false,
                        success = true,
                        error = null,
                        fullName = user.fullName,
                        nationalId = user.nationalId,
                        email = user.email,
                        phoneNumber = user.phoneNumber
                    )
                }
                _eventFlow.emit(AuthUiEvent.NavigateToHome)
            } else {
                _eventFlow.emit(AuthUiEvent.ShowSnackbar(result.exceptionOrNull()?.message ?: "Login failed"))
                _authState.value = _authState.value.copy(
                    isLoading = false,
                    success = false,
                    error = result.exceptionOrNull()?.message ?: "Login failed"
                )
            }
        }
    }

    fun register(username: String, email: String, password: String) {
        viewModelScope.launch {
            // Set loading state
            _authState.value = _authState.value.copy(isLoading = true, error = null)

            // Create user object
            val user = User(
                id = "",
                fullName = username,
                nationalId = "",
                email = email,
                phoneNumber = username,
                password= password
            )

            // Call use case
            val result = registerUseCase(user)

            // Update state based on result
            if (result.isSuccess) {
                val registeredUser = result.getOrNull()
                if (registeredUser != null) {
                    _authState.value = _authState.value.copy(
                        isLoading = false,
                        success = true,
                        error = null,
                        fullName = registeredUser.fullName,
                        nationalId = registeredUser.nationalId,
                        email = registeredUser.email,
                        phoneNumber = registeredUser.phoneNumber
                    )
                }
            } else {
                _authState.value = _authState.value.copy(
                    isLoading = false,
                    success = false,
                    error = result.exceptionOrNull()?.message ?: "Registration failed"
                )
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            // Set loading state
            _authState.value = _authState.value.copy(isLoading = true, error = null)

            // Call use case
            val result = logoutUseCase()

            // Update state based on result
            if (result.isSuccess) {
                _authState.value = AuthState()
            } else {
                _authState.value = _authState.value.copy(
                    isLoading = false,
                    success = false,
                    error = result.exceptionOrNull()?.message ?: "Logout failed"
                )
            }
        }
    }

    fun getCurrentUser() {
        viewModelScope.launch {
            // Set loading state
            _authState.value = _authState.value.copy(isLoading = true, error = null)

            // Call use case
            val result = getCurrentUserUseCase()

            // Update state based on result
            if (result.isSuccess) {
                val user = result.getOrNull()
                if (user != null) {
                    _authState.value = _authState.value.copy(
                        isLoading = false,
                        success = true,
                        error = null,
                        fullName = user.fullName,
                        nationalId = user.nationalId,
                        email = user.email,
                        phoneNumber = user.phoneNumber
                    )
                }
            } else {
                _authState.value = _authState.value.copy(
                    isLoading = false,
                    success = false,
                    error = result.exceptionOrNull()?.message ?: "Failed to get user"
                )
            }
        }
    }

    suspend fun isLoggedIn(): Boolean {
        return isLoggedInUseCase()
    }

    fun sendOTP(phoneNumber: String) {
        viewModelScope.launch {
            // Set loading state
            _authState.value = _authState.value.copy(isLoading = true, error = null)

            // Call use case
            val result = sendOtpUseCase(phoneNumber)

            // Update state based on result
            if (result.isSuccess) {
                _authState.value = _authState.value.copy(
                    isLoading = false,
                    success = true,
                    error = null
                )
            } else {
                _authState.value = _authState.value.copy(
                    isLoading = false,
                    success = false,
                    error = result.exceptionOrNull()?.message ?: "Failed to send OTP"
                )
            }
        }
    }

    fun verifyOTP(phoneNumber: String, otp: String) {
        viewModelScope.launch {
            // Set loading state
            _authState.value = _authState.value.copy(isLoading = true, error = null)

            // Call use case
            val result = verifyOtpUseCase(phoneNumber, otp)

            // Update state based on result
            if (result.isSuccess) {
                val user = result.getOrNull()
                if (user != null) {
                    _authState.value = _authState.value.copy(
                        isLoading = false,
                        success = true,
                        error = null,
                        fullName = user.fullName,
                        nationalId = user.nationalId,
                        email = user.email,
                        phoneNumber = user.phoneNumber,
                        otpValue = otp
                    )
                }
            } else {
                _authState.value = _authState.value.copy(
                    isLoading = false,
                    success = false,
                    error = result.exceptionOrNull()?.message ?: "Invalid OTP"
                )
            }
        }
    }
}
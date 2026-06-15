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
        if (value.length <= 14 && value.all { it.isDigit() }) {
            _state.value = _state.value.copy(nationalId = value)
        }
    }

    fun onEmailChange(value: String) {
        _state.value = _state.value.copy(email = value)
    }

    fun onPhoneNumberChange(value: String) {
        if (value.length <= 11 && value.all { it.isDigit() }) {
            _state.value = _state.value.copy(phoneNumber = value)
        }
    }

    fun onPasswordChange(value: String) {
        _state.value = _state.value.copy(password = value)
    }

    fun register() {
        val currentState = _state.value
        
        // 1. Validation for Arabic Full Name
        val arabicRegex = Regex("^[\\u0621-\\u064A\\s]+$")
        if (currentState.fullName.isBlank()) {
            _state.value = currentState.copy(error = "يرجى إدخال الاسم بالكامل")
            return
        }
        if (!arabicRegex.matches(currentState.fullName)) {
            _state.value = currentState.copy(error = "الاسم يجب أن يكون باللغة العربية فقط")
            return
        }

        // 2. Validation for National ID (14 digits)
        if (currentState.nationalId.length != 14) {
            _state.value = currentState.copy(error = "الرقم القومي يجب أن يتكون من 14 رقم")
            return
        }

        // 3. Validation for Phone Number (Starts with 010, 011, 012, 015 and 11 digits total)
        val phoneRegex = Regex("^01[0125][0-9]{8}$")
        if (!phoneRegex.matches(currentState.phoneNumber)) {
            _state.value = currentState.copy(error = "رقم الهاتف غير صحيح (يجب أن يبدأ بـ 010، 011، 012، أو 015)")
            return
        }

        // 4. Basic Password check
        if (currentState.password.length < 6) {
            _state.value = currentState.copy(error = "كلمة السر يجب أن لا تقل عن 6 أحرف")
            return
        }

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
                _state.value = _state.value.copy(isLoading = false, success = true)
                _eventFlow.emit(RegistrationUiEvent.ShowSnackbar("تم إنشاء الحساب بنجاح!"))
                _eventFlow.emit(RegistrationUiEvent.NavigateToLogin)
            } else {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = result.exceptionOrNull()?.message ?: "فشل إنشاء الحساب"
                )
            }
        }
    }
}

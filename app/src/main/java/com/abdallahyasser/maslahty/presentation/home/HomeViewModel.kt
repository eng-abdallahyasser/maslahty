package com.abdallahyasser.maslahty.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdallahyasser.maslahty.domain.auth.useCase.GetCurrentUserUseCase
import com.abdallahyasser.maslahty.domain.auth.useCase.isLoggedInUseCase
import com.abdallahyasser.maslahty.domain.auth.useCase.logoutUseCase
import com.abdallahyasser.maslahty.domain.common.Result
import com.abdallahyasser.maslahty.domain.common.getOrNull
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val logoutUseCase: logoutUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val isLoggedInUseCase: isLoggedInUseCase
) : ViewModel() {
    private val _screenState = MutableStateFlow(HomeScreenState())
    val screenState = _screenState.asStateFlow()

    init {
        getCurrentUser()
    }

    private fun getCurrentUser() {
        viewModelScope.launch {
            val result = getCurrentUserUseCase()


            if (result is Result.Success<*>) {
                val user = result.getOrNull()
                if (user != null) {
                    _screenState.value = _screenState.value.copy(
                        fullName = user.fullName
                    )
                }
            }
        }
    }

    fun logout(onSuccess: () -> Unit) {
        viewModelScope.launch {
            val result = logoutUseCase()
            if (result.isSuccess) {
                onSuccess()
            }
        }
    }

    suspend fun isLoggedIn(): Boolean {
        return isLoggedInUseCase()
    }
}
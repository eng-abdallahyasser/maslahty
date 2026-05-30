package com.abdallahyasser.maslahty.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdallahyasser.maslahty.domain.auth.useCase.GetCurrentUserUseCase
import com.abdallahyasser.maslahty.domain.auth.useCase.isLoggedInUseCase
import com.abdallahyasser.maslahty.domain.auth.useCase.logoutUseCase
import com.abdallahyasser.maslahty.domain.home.useCase.GetUserDataUseCase
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
    private val isLoggedInUseCase: isLoggedInUseCase,
    private val getUserDataUseCase: GetUserDataUseCase
) : ViewModel() {
    private val _screenState = MutableStateFlow(HomeScreenState())
    val screenState = _screenState.asStateFlow()

    init {
        loadUserData()
    }

    private fun loadUserData() {
        viewModelScope.launch {
            val result = getUserDataUseCase()
            if (result is Result.Success) {
                val data = result.data
                _screenState.value = _screenState.value.copy(
                    fullName = data.fullName,
                    vehiclesNumber = data.vehiclesNumber,
                    activeRequests = data.activeRequests,
                    completedRequests = data.completedRequests
                )
            }
        }
    }

    fun logout(onSuccess: () -> Unit) {
        viewModelScope.launch {
            val result = logoutUseCase()
            if (result.isSuccess) {
                _screenState.value = HomeScreenState()
                onSuccess()
            }
        }
    }

    suspend fun isLoggedIn(): Boolean {
        return isLoggedInUseCase()
    }
}
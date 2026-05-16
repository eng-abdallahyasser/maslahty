package com.abdallahyasser.maslahty.presentation.my_vehicle

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdallahyasser.maslahty.domain.auth.useCase.GetCurrentUserUseCase
import com.abdallahyasser.maslahty.domain.vehicle.usecase.GetUserVehiclesUseCase
import com.abdallahyasser.maslahty.domain.common.Result
import com.abdallahyasser.maslahty.domain.common.getOrNull
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class MyVehicleViewModel @Inject constructor(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val getUserVehiclesUseCase: GetUserVehiclesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(MyVehicleState())
    val state = _state.asStateFlow()

    init {
        loadVehicles()
    }

    fun loadVehicles() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)
            
            // 1. Get current user
            val userResult = getCurrentUserUseCase()
            if (userResult is Result.Success) {
                val userId = userResult.getOrNull()?.id
                if (userId != null) {
                    // 2. Get vehicles for this user
                    when (val result = getUserVehiclesUseCase(userId)) {
                        is Result.Success -> {
                            _state.value = _state.value.copy(
                                isLoading = false,
                                vehicles = result.data
                            )
                        }
                        is Result.Error -> {
                            _state.value = _state.value.copy(
                                isLoading = false,
                                error = result.exception.message ?: "Failed to load vehicles"
                            )
                        }
                        else -> {}
                    }
                } else {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = "User not found"
                    )
                }
            } else {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = "Failed to get current user"
                )
            }
        }
    }
}

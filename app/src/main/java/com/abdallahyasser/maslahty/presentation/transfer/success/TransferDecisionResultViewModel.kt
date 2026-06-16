package com.abdallahyasser.maslahty.presentation.transfer.success

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdallahyasser.maslahty.domain.common.Result
import com.abdallahyasser.maslahty.domain.transfer.entity.TransferRequest
import com.abdallahyasser.maslahty.domain.transfer.repo.TransferRequestRepository
import com.abdallahyasser.maslahty.domain.vehicle.entity.Vehicle
import com.abdallahyasser.maslahty.domain.vehicle.repo.VehicleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransferDecisionResultViewModel @Inject constructor(
    private val transferRequestRepository: TransferRequestRepository,
    private val vehicleRepository: VehicleRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<DecisionResultState>(DecisionResultState.Loading)
    val uiState: StateFlow<DecisionResultState> = _uiState.asStateFlow()

    fun loadDetails(requestId: String) {
        viewModelScope.launch {
            _uiState.value = DecisionResultState.Loading
            when (val requestResult = transferRequestRepository.getTransferRequestById(requestId)) {
                is Result.Success -> {
                    val request = requestResult.data
                    when (val vehicleResult = vehicleRepository.getVehicleById(request.vehicleId)) {
                        is Result.Success -> {
                            _uiState.value = DecisionResultState.Success(
                                request = request,
                                vehicle = vehicleResult.data
                            )
                        }
                        is Result.Error -> {
                            _uiState.value = DecisionResultState.Success(
                                request = request,
                                vehicle = null
                            )
                        }
                        else -> {
                            _uiState.value = DecisionResultState.Success(
                                request = request,
                                vehicle = null
                            )
                        }
                    }
                }
                is Result.Error -> {
                    _uiState.value = DecisionResultState.Error(
                        requestResult.exception.message ?: "فشل في تحميل بيانات الطلب"
                    )
                }
                else -> {
                    _uiState.value = DecisionResultState.Error("حالة تحميل غير معروفة")
                }
            }
        }
    }
}

sealed class DecisionResultState {
    object Loading : DecisionResultState()
    data class Success(val request: TransferRequest, val vehicle: Vehicle?) : DecisionResultState()
    data class Error(val message: String) : DecisionResultState()
}

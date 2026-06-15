package com.abdallahyasser.maslahty.presentation.transfer.vehicleDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdallahyasser.maslahty.data.local.TransferDraft.TransferDraft
import com.abdallahyasser.maslahty.data.local.TransferDraft.TransferDraftStore
import com.abdallahyasser.maslahty.domain.auth.useCase.GetCurrentUserUseCase
import com.abdallahyasser.maslahty.domain.vehicle.entity.Vehicle
import com.abdallahyasser.maslahty.domain.vehicle.entity.VehicleCondition
import com.abdallahyasser.maslahty.domain.vehicle.repo.VehicleRepository
import com.abdallahyasser.maslahty.domain.common.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Date

@HiltViewModel
class VehicleViewModel @Inject constructor(
    private val vehicleRepository: VehicleRepository,
    private val getCurrentUserUseCase: GetCurrentUserUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(VehicleState.VehicleIdentificationState())
    var uiState : StateFlow<VehicleState.VehicleIdentificationState> = _uiState.asStateFlow()

    init {
        loadCurrentUserInfo()
    }

    private fun loadCurrentUserInfo() {
        viewModelScope.launch {
            when (val result = getCurrentUserUseCase()) {
                is Result.Success -> {
                    val user = result.data
                    _uiState.value = _uiState.value.copy(
                        ownerName = user.fullName,
                        ownerNationalId = user.nationalId
                    )
                }
                else -> {}
            }
        }
    }

    fun loadVehicleData(vehicleId: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            when (val result = vehicleRepository.getVehicleById(vehicleId)) {
                is Result.Success -> {
                    val vehicle = result.data
                    _uiState.value = _uiState.value.copy(
                        licensePlate = vehicle.licensePlate,
                        chassisNumber = vehicle.chassisNumber,
                        engineNumber = vehicle.engineNumber,
                        isReadOnly = true,
                        isLoading = false,
                        error = null
                    )
                }
                is Result.Error -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = result.exception.message ?: "فشل في جلب بيانات المركبة"
                    )
                }
                else -> {
                    _uiState.value = _uiState.value.copy(isLoading = false)
                }
            }
        }
    }

    fun onLicensePlateChange(value: String) {
        if (!_uiState.value.isReadOnly) {
            _uiState.value = _uiState.value.copy(licensePlate = value.uppercase())
        }
    }

    fun onChassisNumberChange(value: String) {
        if (!_uiState.value.isReadOnly) {
            _uiState.value = _uiState.value.copy(chassisNumber = value.uppercase())
        }
    }

    fun onEngineNumberChange(value: String) {
        if (!_uiState.value.isReadOnly) {
            _uiState.value = _uiState.value.copy(engineNumber = value.uppercase())
        }
    }

    fun onNewOwnerNationalIdChange(value: String) {
        if (value.length <= 14 && value.all { it.isDigit() }) {
            _uiState.value = _uiState.value.copy(newOwnerNationalId = value)
        }
    }

    fun setError(message: String?) {
        _uiState.value = _uiState.value.copy(error = message)
    }

    fun setLoading(isLoading: Boolean) {
        _uiState.value = _uiState.value.copy(isLoading = isLoading)
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }

    fun saveVehicleDataAndNavigate(licensePlate: String, onNavigate: () -> Unit) {
        val state = _uiState.value

        val vehicle = Vehicle(
            id = licensePlate,
            ownerId = state.ownerNationalId,
            licensePlate = licensePlate,
            chassisNumber = state.chassisNumber,
            engineNumber = state.engineNumber,
            model = "Unknown",
            manufacturingYear = 2024,
            color = "Unknown",
            kilometers = 0,
            condition = VehicleCondition.GOOD,
            licenseImageUrl = null,
            vehicleImageUrl = null,
            chassisImageUrl = null,
            engineImageUrl = null,
            contractImageUrl = null,
            createdAt = Date(),
            updatedAt = Date()
        )

        val activeId = TransferDraftStore.activeDraftId
        val mergedVehicle = if (activeId != null && activeId != licensePlate) {
            val prevDraft = TransferDraftStore.drafts[activeId]
            if (prevDraft != null) {
                vehicle.copy(
                    licenseImageUrl = prevDraft.vehicle.licenseImageUrl ?: vehicle.licenseImageUrl,
                    vehicleImageUrl = prevDraft.vehicle.vehicleImageUrl ?: vehicle.vehicleImageUrl,
                    chassisImageUrl = prevDraft.vehicle.chassisImageUrl ?: vehicle.chassisImageUrl,
                    engineImageUrl = prevDraft.vehicle.engineImageUrl ?: vehicle.engineImageUrl,
                    contractImageUrl = prevDraft.vehicle.contractImageUrl ?: vehicle.contractImageUrl,
                    updatedAt = Date()
                ).also {
                    TransferDraftStore.drafts.remove(activeId)
                }
            } else vehicle
        } else vehicle

        val draft = TransferDraft(
            vehicle = mergedVehicle,
            salePrice = null,
            marketPrice = null,
            notes = "",
            buyerNationalId = state.newOwnerNationalId
        )

        TransferDraftStore.drafts[licensePlate] = draft
        TransferDraftStore.activeDraftId = licensePlate

        onNavigate()
    }
}
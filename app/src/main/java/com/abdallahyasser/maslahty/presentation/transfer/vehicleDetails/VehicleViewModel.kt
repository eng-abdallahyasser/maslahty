package com.abdallahyasser.maslahty.presentation.transfer.vehicleDetails

import androidx.lifecycle.ViewModel
import com.abdallahyasser.maslahty.data.local.TransferDraft.TransferDraft
import com.abdallahyasser.maslahty.data.local.TransferDraft.TransferDraftStore
import com.abdallahyasser.maslahty.domain.vehicle.entity.Vehicle
import com.abdallahyasser.maslahty.domain.vehicle.entity.VehicleCondition
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Date

@HiltViewModel
class VehicleDetailsViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(VehicleState.VehicleIdentificationState())
    var uiState : StateFlow<VehicleState.VehicleIdentificationState> = _uiState.asStateFlow()


    fun onLicensePlateChange(value: String) {
        _uiState.value = _uiState.value.copy(licensePlate = value.uppercase())
    }

    fun onChassisNumberChange(value: String) {
        _uiState.value = _uiState.value.copy(chassisNumber = value.uppercase())
    }

    fun onEngineNumberChange(value: String) {
        _uiState.value = _uiState.value.copy(engineNumber = value.uppercase())
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
    // ✅ هذه الدالة الجديدة تحفظ البيانات
    fun saveVehicleDataAndNavigate(licensePlate: String, onNavigate: () -> Unit) {
        val state = _uiState.value

        // استيراد الـ imports
        val vehicle = Vehicle(
            id = licensePlate,
            ownerId = "user1",
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

        // If there was an active temporary draft (e.g. created when starting from Contract screen),
        // merge images/data from it into the new draft and remove the temp entry.
        val activeId = TransferDraftStore.activeDraftId
        val mergedVehicle = if (activeId != null && activeId != licensePlate) {
            val prevDraft = TransferDraftStore.drafts[activeId]
            if (prevDraft != null) {
                // copy media fields from previous draft's vehicle
                vehicle.copy(
                    licenseImageUrl = prevDraft.vehicle.licenseImageUrl ?: vehicle.licenseImageUrl,
                    vehicleImageUrl = prevDraft.vehicle.vehicleImageUrl ?: vehicle.vehicleImageUrl,
                    chassisImageUrl = prevDraft.vehicle.chassisImageUrl ?: vehicle.chassisImageUrl,
                    engineImageUrl = prevDraft.vehicle.engineImageUrl ?: vehicle.engineImageUrl,
                    contractImageUrl = prevDraft.vehicle.contractImageUrl ?: vehicle.contractImageUrl,
                    updatedAt = Date()
                ).also {
                    // remove the old temporary draft
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

        // حفظ في TransferDraftStore
        TransferDraftStore.drafts[licensePlate] = draft
        TransferDraftStore.activeDraftId = licensePlate

        onNavigate()
    }

}
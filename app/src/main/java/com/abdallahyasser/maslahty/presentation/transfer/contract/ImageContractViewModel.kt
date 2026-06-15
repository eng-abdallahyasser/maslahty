package com.abdallahyasser.maslahty.presentation.transfer.contract

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdallahyasser.maslahty.data.local.TransferDraft.TransferDraftStore
import com.abdallahyasser.maslahty.data.local.TransferDraft.TransferDraft
import com.abdallahyasser.maslahty.domain.vehicle.repo.VehicleRepository
import com.abdallahyasser.maslahty.domain.common.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date

@HiltViewModel
class ImageContractViewModel @Inject constructor(
    private val vehicleRepository: VehicleRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(ImageContractState())
    val uiState: StateFlow<ImageContractState> = _uiState.asStateFlow()

    fun updateContractImage(url: String) = _uiState.update { it.copy(imageContract = url, error = null) }

    fun loadDraftOrInitialize(vehicleId: String) {
        // If it looks like a temporary timestamp, just use/create local draft.
        val isTempId = vehicleId.length >= 13 && vehicleId.all { it.isDigit() }
        val existingDraft = TransferDraftStore.drafts[vehicleId]
        if (existingDraft != null) {
            _uiState.update {
                it.copy(
                    imageContract = existingDraft.vehicle.contractImageUrl ?: "",
                    error = null
                )
            }
            return
        }

        if (isTempId) {
            // No DB lookup for temporary IDs, just ensure it has default blank values
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            when (val result = vehicleRepository.getVehicleById(vehicleId)) {
                is Result.Success -> {
                    val vehicle = result.data
                    val draft = TransferDraft(
                        vehicle = vehicle,
                        salePrice = null,
                        marketPrice = null,
                        notes = "",
                        buyerNationalId = ""
                    )
                    TransferDraftStore.drafts[vehicleId] = draft
                    _uiState.update {
                        it.copy(
                            imageContract = vehicle.contractImageUrl ?: "",
                            isLoading = false,
                            error = null
                        )
                    }
                }
                is Result.Error -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = result.exception.message ?: "فشل في جلب بيانات المركبة"
                        )
                    }
                }
                else -> {
                    _uiState.update { it.copy(isLoading = false) }
                }
            }
        }
    }

    fun onNextClicked(vehicleId: String, onNavigate: () -> Unit) {
        val state = _uiState.value
        val draft = TransferDraftStore.drafts[vehicleId]

        when {
            state.uploadedCount < 1 -> {
                _uiState.update { it.copy(error = "لم يتم رفع صوره") }
            }

            draft == null -> {
                // ❌ بدل ما تطلع Error، الآن يجب تكون البيانات موجودة
                _uiState.update { it.copy(error = "لم يتم العثور على بيانات المركبة. الرجاء المحاولة من جديد") }
            }

            else -> {
                // ✅ حفظ الصور في البيانات
                TransferDraftStore.drafts[vehicleId] = draft.copy(
                    vehicle = draft.vehicle.copy(
                        contractImageUrl = state.imageContract,
                        updatedAt = Date()
                    )
                )
                // mark this as the active draft for the ongoing flow
                TransferDraftStore.activeDraftId = vehicleId
                onNavigate()
            }
        }
    }
}
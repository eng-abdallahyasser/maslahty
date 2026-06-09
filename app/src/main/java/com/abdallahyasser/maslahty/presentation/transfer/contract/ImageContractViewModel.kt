package com.abdallahyasser.maslahty.presentation.transfer.contract

import androidx.lifecycle.ViewModel
import com.abdallahyasser.maslahty.data.local.TransferDraft.TransferDraftStore
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.Date
@HiltViewModel
class ImageContractViewModel @Inject constructor(): ViewModel() {
    private val _uiState = MutableStateFlow(ImageContractState())
    val uiState: StateFlow<ImageContractState> = _uiState.asStateFlow()
    fun updateContractImage(url: String) = _uiState.update { it.copy(imageContract = url, error = null) }

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
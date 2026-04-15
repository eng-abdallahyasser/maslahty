package com.abdallahyasser.maslahty.presentaion.screens.vechicle.ImageUpload

import androidx.lifecycle.ViewModel
import com.abdallahyasser.maslahty.data.local.TransferDraft.TransferDraftStore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.Date



// TODO: Implement the ViewModel for handling image upload
class ImageUploadViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(ImageUploadState())
    val uiState = _uiState.asStateFlow()

    // دوال تحديث الروابط
    fun updateLicenseImage(url: String) = _uiState.update { it.copy(licenseImageUrl = url, error = null) }
    fun updateVehicleImage(url: String) = _uiState.update { it.copy(vehicleImageUrl = url, error = null) }
    fun updateChassisImage(url: String) = _uiState.update { it.copy(chassisImageUrl = url, error = null) }
    fun updateEngineImage(url: String) = _uiState.update { it.copy(engineImageUrl = url, error = null) }

    fun onNextClicked(vehicleId: String, onNavigate: () -> Unit) {
        val state = _uiState.value
        // محاولة الحصول على الـ draft أو عمل واحد جديد لو null
        val draft = TransferDraftStore.drafts[vehicleId]

        when {
            state.uploadedCount < 4 -> {
                _uiState.update { it.copy(error = "كل الصور الأربع إلزامية قبل المتابعة") }
            }
            // لو مفيش draft، ممكن نكريت واحد جديد (للتجربة) بدل ما نطلع إيرور
            draft == null -> {
                // هنجرب نصلح ده هنا عشان الـ UI ميعلقش
                _uiState.update { it.copy(error = "خطأ: بيانات المركبة غير موجودة في الـ DraftStore") }
            }
            else -> {
                // الكود بيكمل عادي لو الـ draft موجود
                TransferDraftStore.drafts[vehicleId] = draft.copy(
                    vehicle = draft.vehicle.copy(
                        licenseImageUrl = state.licenseImageUrl,
                        vehicleImageUrl = state.vehicleImageUrl,
                        chassisImageUrl = state.chassisImageUrl,
                        engineImageUrl = state.engineImageUrl,
                        updatedAt = Date()
                    )
                )
                onNavigate()
            }
        }
    }

}
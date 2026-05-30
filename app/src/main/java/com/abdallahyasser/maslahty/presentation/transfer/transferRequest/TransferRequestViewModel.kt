package com.abdallahyasser.maslahty.presentation.transfer.transferRequest

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdallahyasser.maslahty.data.local.TransferDraft.TransferDraft
import com.abdallahyasser.maslahty.domain.auth.useCase.GetCurrentUserUseCase
import com.abdallahyasser.maslahty.domain.common.Result
import com.abdallahyasser.maslahty.domain.transfer.entity.TransferRequest
import com.abdallahyasser.maslahty.domain.transfer.entity.TransferStatus
import com.abdallahyasser.maslahty.domain.transfer.usecase.CreateTransferRequestUseCase
import com.abdallahyasser.maslahty.domain.transfer.usecase.ValidateTransferRequestUseCase
import com.abdallahyasser.maslahty.domain.utils.PriceValidationUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date

@HiltViewModel
class TransferRequestViewModel @Inject constructor(
    private val createTransferRequestUseCase: CreateTransferRequestUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val validateTransferRequestUseCase: ValidateTransferRequestUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(TransferRequestUiState())
    val uiState = _uiState.asStateFlow()

    fun initData(vehicleId: String, draft: TransferDraft?) {
        _uiState.update { it.copy(
            vehicleId = vehicleId,
            draft = draft,
            buyerNationalId = draft?.buyerNationalId.orEmpty(),
            notes = draft?.notes.orEmpty()
        ) }
    }

    fun onNationalIdChange(newValue: String) {
        if (newValue.length <= 14 && newValue.all { it.isDigit() }) {
            _uiState.update { it.copy(buyerNationalId = newValue, error = null) }
        }
    }

    fun onNotesChange(newValue: String) {
        _uiState.update { it.copy(notes = newValue) }
    }

    fun submitRequest() {
        val currentState = _uiState.value
        val draft = currentState.draft
        val salePrice = draft?.salePrice

        if (draft == null || salePrice == null) {
            _uiState.update { it.copy(error = "بيانات الطلب غير مكتملة") }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            // 1. Get current user (seller) info
            val currentUserResult = getCurrentUserUseCase()
            if (currentUserResult is Result.Error) {
                _uiState.update { it.copy(isLoading = false, error = "فشل في الحصول على بيانات المستخدم") }
                return@launch
            }
            val currentUser = (currentUserResult as Result.Success).data

            // 2. Construct the TransferRequest entity
            val request = TransferRequest(
                id = "", // Will be generated in data layer
                vehicleId = currentState.vehicleId,
                sellerId = currentUser.id,
                buyerId = null,
                buyerNationalId = currentState.buyerNationalId,
                buyerName = "المشتري", // Placeholder or fetch if possible
                sellerName = currentUser.fullName,
                price = salePrice,
                status = TransferStatus.PENDING,
                imageUrls = listOfNotNull(
                    draft.vehicle.licenseImageUrl,
                    draft.vehicle.vehicleImageUrl,
                    draft.vehicle.chassisImageUrl,
                    draft.vehicle.engineImageUrl
                ),
                notes = currentState.notes,
                priceWarning = PriceValidationUtil.validatePrice(salePrice, draft.marketPrice ?: 0.0),
                createdAt = Date(),
                updatedAt = Date()
            )

            // 3. Validate the request using domain logic
            val validationResult = validateTransferRequestUseCase(request)
            if (!validationResult.successful) {
                _uiState.update { it.copy(isLoading = false, error = validationResult.errorMessage) }
                return@launch
            }

            // 4. Execute the save request
            when (val result = createTransferRequestUseCase(request)) {
                is Result.Success -> {
                    _uiState.update { it.copy(isLoading = false, isSuccess = true) }
                }
                is Result.Error -> {
                    _uiState.update { it.copy(isLoading = false, error = result.exception.message) }
                }
                else -> {}
            }
        }
    }
}
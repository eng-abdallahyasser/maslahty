package com.abdallahyasser.maslahty.presentaion.screens.vechicle.TransferRequest

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdallahyasser.maslahty.data.local.TransferDraft.TransferDraft
import com.example.maslahty.domain.utils.PriceValidationUtil
import com.example.maslahty.domain.utils.ValidationUtil
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TransferRequestViewModel () : ViewModel() {

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
        val salePrice = currentState.draft?.salePrice

        // الـ Validation مكانه هنا في الـ ViewModel
        when {
            currentState.draft == null || salePrice == null -> {
                _uiState.update { it.copy(error = "بيانات الطلب غير مكتملة") }
            }
            !ValidationUtil.isValidNationalId(currentState.buyerNationalId) -> {
                _uiState.update { it.copy(error = "الرقم القومي للمشتري غير صحيح") }
            }
            else -> {
                executeCreateRequest(currentState, salePrice)
            }
        }
    }

    private fun executeCreateRequest(state: TransferRequestUiState, salePrice: Double) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            // هنا بتنادي الـ UseCase أو الـ Repository
            // محاكاة لعملية الإرسال:
            try {
                val warningObj = state.draft?.marketPrice?.let {
                    PriceValidationUtil.validatePrice(salePrice, it)
                }

                // افترضنا إن فيه ميثود في الـ ViewModel أو Repository بتبعت الداتا
                // repository.send(request)

                _uiState.update { it.copy(isLoading = false, isSuccess = true) }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = e.message) }
            }
        }
    }
}
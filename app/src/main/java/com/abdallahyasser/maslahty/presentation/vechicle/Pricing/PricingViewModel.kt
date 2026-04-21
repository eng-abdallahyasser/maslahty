package com.abdallahyasser.maslahty.presentation.vechicle.Pricing

import androidx.lifecycle.ViewModel
import com.abdallahyasser.maslahty.data.local.TransferDraft.TransferDraftStore
import com.abdallahyasser.maslahty.domain.transfer.entity.PriceWarning
import com.example.maslahty.domain.utils.PriceValidationUtil
import com.example.maslahty.domain.utils.ValidationUtil
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class PricingViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(PricingUiState())
    val uiState = _uiState.asStateFlow()

    fun loadMarketPrice(vehicleId: String) {
        val draft = TransferDraftStore.drafts[vehicleId]
        draft?.vehicle?.let { vehicle ->
            val estimatedPrice = PriceValidationUtil.getMarketPriceEstimate(
                model = vehicle.model,
                year = vehicle.manufacturingYear,
                kilometers = vehicle.kilometers,
                condition = vehicle.condition.name
            )
            _uiState.update { it.copy(
                marketPrice = estimatedPrice,
                salePriceInput = draft.salePrice?.toString() ?: ""
                // لاحظ: لم نضع askedPrice هنا لأنه يُحسب تلقائياً
            ) }
        }
    }

    fun onPriceChange(input: String) {
        // تنظيف المدخلات (أرقام فقط)
        val filtered = input.filter { it.isDigit() || it == '.' }

        _uiState.update { it.copy(
            salePriceInput = filtered,
            error = null
            // لاحظ: بمجرد تحديث salePriceInput، قيمة askedPrice ستتغير لوحدها
        ) }
    }

    fun onNextClicked(vehicleId: String, onNavigate: () -> Unit) {
        val state = _uiState.value
        val draft = TransferDraftStore.drafts[vehicleId]
        println("Debug: vehicleId = $vehicleId") // اطبع الـ ID لتتأكد منه
        println("Debug: draft exists = ${draft != null}") // هل المس

        when {
            draft == null -> _uiState.update { it.copy(error = "تعذر تحميل بيانات المركبة") }
            // البرنامج هنا سيقرأ askedPrice المشتقة تلقائياً من النص
            !ValidationUtil.isValidPrice(state.askedPrice) -> {
                _uiState.update { it.copy(error = "أدخل سعراً صحيحاً") }
            }
            else -> {
                TransferDraftStore.drafts[vehicleId] = draft.copy(
                    salePrice = state.askedPrice,
                    marketPrice = state.marketPrice
                )
                onNavigate()
            }
        }
    }

    fun getPriceWarning(): PriceWarning? {
        val state = _uiState.value
        return if (state.askedPrice > 0 && state.marketPrice > 0) {
            PriceValidationUtil.validatePrice(state.askedPrice, state.marketPrice)
        } else null
    }
}
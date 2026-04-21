package com.abdallahyasser.maslahty.presentation.vechicle.Pricing

data class PricingUiState(

    var salePriceInput: String = "",
    val marketPrice: Double = 0.0,
    val error: String? = null,
    val isLoading: Boolean = false
) {
    // بيانات مشتقة (Derived State) للحسابات
    val askedPrice: Double get() = salePriceInput.toDoubleOrNull() ?: 0.0

    val priceDifference: Double get() = askedPrice - marketPrice

    val differencePercentage: Double
        get() = if (marketPrice > 0) (priceDifference / marketPrice * 100) else 0.0
}

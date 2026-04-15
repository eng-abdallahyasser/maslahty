package com.abdallahyasser.maslahty.presentaion.screens.vechicle.VehicleDetails

sealed class VehicleState {
    data class VehicleIdentificationState(
        val licensePlate: String = "",
        val chassisNumber: String = "",
        val engineNumber: String = "",
        val buyerNationalId: String = "",
        val error: String? = null,
        val isLoading: Boolean = false,
        val isVerified: Boolean = false,
        val newOwnerNationalId: String = "",
    )


    data class PricingState(
        val salePrice: String = "",
        val marketPrice: Double = 0.0,
        val priceWarning: String? = null
    )
}
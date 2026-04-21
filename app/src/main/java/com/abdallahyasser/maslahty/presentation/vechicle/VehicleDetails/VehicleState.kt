package com.abdallahyasser.maslahty.presentation.vechicle.VehicleDetails

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



}
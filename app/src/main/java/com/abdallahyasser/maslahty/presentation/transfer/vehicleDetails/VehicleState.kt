package com.abdallahyasser.maslahty.presentation.transfer.vehicleDetails

sealed class VehicleState {
    data class VehicleIdentificationState(
        val licensePlate: String = "",
        val chassisNumber: String = "",
        val engineNumber: String = "",
        val model: String = "",
        val manufacturingYear: String = "",
        val color: String = "",
        val buyerNationalId: String = "",
        val error: String? = null,
        val isLoading: Boolean = false,
        val isVerified: Boolean = false,
        val newOwnerNationalId: String = "",
        val isReadOnly: Boolean = false,
        val ownerName: String = "",
        val ownerNationalId: String = ""
    )
}
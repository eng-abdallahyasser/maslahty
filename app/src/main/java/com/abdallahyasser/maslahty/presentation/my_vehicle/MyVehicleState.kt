package com.abdallahyasser.maslahty.presentation.my_vehicle

import com.abdallahyasser.maslahty.domain.vehicle.entity.Vehicle

data class MyVehicleState(
    val isLoading: Boolean = false,
    val vehicles: List<Vehicle> = emptyList(),
    val error: String? = null
)

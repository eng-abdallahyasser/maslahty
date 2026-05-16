package com.abdallahyasser.maslahty.domain.vehicle.usecase

import com.abdallahyasser.maslahty.domain.vehicle.entity.Vehicle
import com.abdallahyasser.maslahty.domain.common.Result
import com.abdallahyasser.maslahty.domain.vehicle.repo.VehicleRepository
import jakarta.inject.Inject

class AddVehicleUseCase @Inject constructor(
    private val vehicleRepository: VehicleRepository
) {
    suspend operator fun invoke(vehicle: Vehicle): Result<Vehicle> {
        return vehicleRepository.createVehicle(vehicle)
    }
}

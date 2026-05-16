package com.abdallahyasser.maslahty.domain.vehicle.usecase

import com.abdallahyasser.maslahty.domain.vehicle.entity.Vehicle
import com.abdallahyasser.maslahty.domain.common.Result
import com.abdallahyasser.maslahty.domain.vehicle.repo.VehicleRepository
import jakarta.inject.Inject

class GetVehicleByPlateUseCase @Inject constructor(
    private val vehicleRepository: VehicleRepository
) {
    suspend operator fun invoke(licensePlate: String): Result<Vehicle> {
        return vehicleRepository.getVehicleByPlate(licensePlate)
    }
}

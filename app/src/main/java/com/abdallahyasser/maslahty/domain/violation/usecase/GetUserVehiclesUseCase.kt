package com.abdallahyasser.maslahty.domain.violation.usecase

import com.abdallahyasser.maslahty.domain.common.Result
import com.abdallahyasser.maslahty.domain.vehicle.entity.Vehicle
import com.abdallahyasser.maslahty.domain.vehicle.repo.VehicleRepository

class GetUserVehiclesUseCase(private val vehicleRepository: VehicleRepository) {
    suspend operator fun invoke(userId: String): kotlin.Result<List<Vehicle>> {
        return vehicleRepository.getUserVehicles(userId)
    }
}

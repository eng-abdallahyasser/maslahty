package com.abdallahyasser.maslahty.domain.vehicle.usecase

import com.abdallahyasser.maslahty.domain.vehicle.entity.Vehicle
import com.abdallahyasser.maslahty.domain.vehicle.repo.VehicleRepository
import com.abdallahyasser.maslahty.domain.common.Result
import jakarta.inject.Inject

class GetUserVehiclesUseCase @Inject constructor(
    private val repository: VehicleRepository
) {
    suspend operator fun invoke(userId: String): Result<List<Vehicle>> {
        return repository.getUserVehicles(userId)
    }
}

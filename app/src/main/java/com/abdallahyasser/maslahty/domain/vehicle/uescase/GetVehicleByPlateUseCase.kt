package com.abdallahyasser.maslahty.domain.usecases.vehicle
import com.abdallahyasser.maslahty.domain.vehicle.entity.Vehicle
import com.abdallahyasser.maslahty.domain.common.Result
import com.abdallahyasser.maslahty.domain.vehicle.repo.VehicleRepository

class GetVehicleByPlateUseCase(private val vehicleRepository: VehicleRepository) {
    suspend operator fun invoke(licensePlate: String): Result<Vehicle> {
        return vehicleRepository.getVehicleByPlate(licensePlate)
    }
}


package com.abdallahyasser.maslahty.data.vehicle.repoImpl

import com.abdallahyasser.maslahty.domain.vehicle.entity.Vehicle
import com.example.maslahty.domain.repositories.VehicleRepository
import com.example.maslahty.domain.common.Result

class VehicleRepositoryImpl: VehicleRepository {
    override suspend fun getVehicleByPlate(licensePlate: String): Result<Vehicle>{
        TODO()
    }

    override suspend fun getVehicleById(id: String): Result<Vehicle> {
        TODO("Not yet implemented")
    }

    override suspend fun createVehicle(vehicle: Vehicle): Result<Vehicle> {
        TODO("Not yet implemented")
    }

    override suspend fun updateVehicle(vehicle: Vehicle): Result<Vehicle> {
        TODO("Not yet implemented")
    }

    override suspend fun getUserVehicles(userId: String): Result<List<Vehicle>> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteVehicle(id: String): Result<Unit> {
        TODO("Not yet implemented")
    }
}

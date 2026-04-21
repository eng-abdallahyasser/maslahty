package com.abdallahyasser.maslahty.domain.vehicle.repo

import com.abdallahyasser.maslahty.domain.vehicle.entity.Vehicle
import com.abdallahyasser.maslahty.domain.common.Result

interface VehicleRepository {
    suspend fun getVehicleByPlate(licensePlate: String): Result<Vehicle>
    suspend fun getVehicleById(id: String): Result<Vehicle>
    suspend fun createVehicle(vehicle: Vehicle): Result<Vehicle>
    suspend fun updateVehicle(vehicle: Vehicle): Result<Vehicle>
    suspend fun getUserVehicles(userId: String): kotlin.Result<List<Vehicle>>
    suspend fun deleteVehicle(id: String): Result<Unit>
}


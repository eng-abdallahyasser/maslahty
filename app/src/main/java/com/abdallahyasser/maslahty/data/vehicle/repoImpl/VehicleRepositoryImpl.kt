package com.abdallahyasser.maslahty.data.vehicle.repoImpl

import com.abdallahyasser.maslahty.domain.vehicle.entity.Vehicle
import com.abdallahyasser.maslahty.domain.vehicle.repo.VehicleRepository
import com.abdallahyasser.maslahty.domain.common.Result
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import jakarta.inject.Inject


class VehicleRepositoryImpl @Inject constructor(
    private val authService: FirebaseAuth,
    private val firestoreService: FirebaseFirestore
) : VehicleRepository {
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

    override suspend fun getUserVehicles(userId: String): kotlin.Result<List<Vehicle>> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteVehicle(id: String): Result<Unit> {
        TODO("Not yet implemented")
    }
}

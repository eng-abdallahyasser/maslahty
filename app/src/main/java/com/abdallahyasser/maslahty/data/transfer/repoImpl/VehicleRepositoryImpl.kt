package com.abdallahyasser.maslahty.data.repoImpl

import com.abdallahyasser.maslahty.domain.vehicle.entity.Vehicle
import com.abdallahyasser.maslahty.domain.vehicle.entity.VehicleCondition
import com.abdallahyasser.maslahty.domain.common.Result
import com.abdallahyasser.maslahty.domain.vehicle.repo.VehicleRepository
import java.util.Date

class VehicleRepositoryImpl : VehicleRepository {

	override suspend fun getVehicleByPlate(licensePlate: String): Result<Vehicle> {
		return try {
			Result.Success(mockVehicle(id = "V-8822", ownerId = "user1", plate = licensePlate))
		} catch (e: Exception) {
			Result.Error(e)
		}
	}

	override suspend fun getVehicleById(id: String): Result<Vehicle> {
		return try {
			Result.Success(mockVehicle(id = id, ownerId = "user1", plate = "ق س س 123"))
		} catch (e: Exception) {
			Result.Error(e)
		}
	}

	override suspend fun createVehicle(vehicle: Vehicle): Result<Vehicle> {
		return try {
			Result.Success(vehicle.copy(id = "V-${(1000..9999).random()}", createdAt = Date(), updatedAt = Date()))
		} catch (e: Exception) {
			Result.Error(e)
		}
	}

	override suspend fun updateVehicle(vehicle: Vehicle): Result<Vehicle> {
		return try {
			Result.Success(vehicle.copy(updatedAt = Date()))
		} catch (e: Exception) {
			Result.Error(e)
		}
	}

	override suspend fun getUserVehicles(userId: String): kotlin.Result<List<Vehicle>> {
		return try {
			kotlin.Result.success(
				listOf(
					mockVehicle("V-8822", userId, "ق س س 123", "Toyota Camry 2022", 2022, "أبيض", 50000, VehicleCondition.GOOD),
					mockVehicle("V-8823", userId, "ق س س 124", "Honda Civic 2020", 2020, "أسود", 75000, VehicleCondition.GOOD),
					mockVehicle("V-8824", userId, "ق س س 125", "BMW 320i 2023", 2023, "رمادي", 20000, VehicleCondition.EXCELLENT)
				)
			)
		} catch (e: Exception) {
			kotlin.Result.failure(e)
		}
	}

	override suspend fun deleteVehicle(id: String): Result<Unit> {
		return try {
			Result.Success(Unit)
		} catch (e: Exception) {
			Result.Error(e)
		}
	}

	private fun mockVehicle(
		id: String,
		ownerId: String,
		plate: String,
		model: String = "Toyota Camry 2022",
		year: Int = 2022,
		color: String = "أبيض",
		kilometers: Int = 50000,
		condition: VehicleCondition = VehicleCondition.GOOD
	): Vehicle {
		val now = Date()
		return Vehicle(
			id = id,
			ownerId = ownerId,
			licensePlate = plate,
			chassisNumber = "CHASSIS$id",
			engineNumber = "ENGINE$id",
			model = model,
			manufacturingYear = year,
			color = color,
			kilometers = kilometers,
			condition = condition,
			licenseImageUrl = null,
			vehicleImageUrl = null,
			chassisImageUrl = null,
			engineImageUrl = null,
			createdAt = Date(now.time - 180L * 24 * 60 * 60 * 1000),
			updatedAt = now
		)
	}
}


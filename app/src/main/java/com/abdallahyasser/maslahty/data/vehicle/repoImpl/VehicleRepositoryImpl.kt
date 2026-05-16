package com.abdallahyasser.maslahty.data.vehicle.repoImpl

import com.abdallahyasser.maslahty.domain.vehicle.entity.Vehicle
import com.abdallahyasser.maslahty.domain.vehicle.entity.VehicleCondition
import com.abdallahyasser.maslahty.domain.vehicle.repo.VehicleRepository
import com.abdallahyasser.maslahty.domain.common.Result
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FieldValue
import jakarta.inject.Inject
import kotlinx.coroutines.tasks.await
import java.util.Date


class VehicleRepositoryImpl @Inject constructor(
    private val authService: FirebaseAuth,
    private val firestoreService: FirebaseFirestore
) : VehicleRepository {

    override suspend fun getVehicleByPlate(licensePlate: String): Result<Vehicle> {
        return try {
            val query = firestoreService.collection("vehicle")
                .whereEqualTo("licensePlate", licensePlate)
                .get()
                .await()

            val document = query.documents.firstOrNull()
            if (document != null && document.exists()) {
                Result.Success(mapToVehicle(document))
            } else {
                Result.Error(Exception("Vehicle not found with plate: $licensePlate"))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun getVehicleById(id: String): Result<Vehicle> {
        return try {
            val document = firestoreService.collection("vehicle").document(id).get().await()
            if (document.exists()) {
                Result.Success(mapToVehicle(document))
            } else {
                Result.Error(Exception("Vehicle not found with id: $id"))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun createVehicle(vehicle: Vehicle): Result<Vehicle> {
        return try {
            val vehicleRef = firestoreService.collection("vehicle").document()
            val vehicleId = vehicleRef.id
            val now = Date()
            val updatedVehicle = vehicle.copy(
                id = vehicleId,
                createdAt = now,
                updatedAt = now
            )

            firestoreService.runBatch { batch ->
                // 1. Create vehicle document
                batch.set(vehicleRef, updatedVehicle)

                // 2. Update user's vehicles list
                val userRef = firestoreService.collection("users").document(vehicle.ownerId)
                batch.update(userRef, "vehicles", FieldValue.arrayUnion(vehicleId))
            }.await()

            Result.Success(updatedVehicle)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun updateVehicle(vehicle: Vehicle): Result<Vehicle> {
        return try {
            val now = Date()
            val updatedVehicle = vehicle.copy(updatedAt = now)
            firestoreService.collection("vehicle")
                .document(vehicle.id)
                .set(updatedVehicle)
                .await()
            Result.Success(updatedVehicle)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun getUserVehicles(userId: String): Result<List<Vehicle>> {
        return try {
            val query = firestoreService.collection("vehicle")
                .whereEqualTo("ownerId", userId)
                .get()
                .await()
            
            val vehicles = query.documents.map { mapToVehicle(it) }
            Result.Success(vehicles)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun deleteVehicle(id: String): Result<Unit> {
        return try {
            // We need ownerId to remove the ID from the user document.
            val vehicleDoc = firestoreService.collection("vehicle").document(id).get().await()
            val ownerId = vehicleDoc.getString("ownerId")

            firestoreService.runBatch { batch ->
                batch.delete(firestoreService.collection("vehicle").document(id))
                if (ownerId != null) {
                    val userRef = firestoreService.collection("users").document(ownerId)
                    batch.update(userRef, "vehicles", FieldValue.arrayRemove(id))
                }
            }.await()

            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    private fun mapToVehicle(doc: com.google.firebase.firestore.DocumentSnapshot): Vehicle {
        return Vehicle(
            id = doc.getString("id") ?: doc.id,
            ownerId = doc.getString("ownerId") ?: "",
            licensePlate = doc.getString("licensePlate") ?: "",
            chassisNumber = doc.getString("chassisNumber") ?: "",
            engineNumber = doc.getString("engineNumber") ?: "",
            model = doc.getString("model") ?: "",
            manufacturingYear = doc.getLong("manufacturingYear")?.toInt() ?: 0,
            color = doc.getString("color") ?: "",
            kilometers = doc.getLong("kilometers")?.toInt() ?: 0,
            condition = try {
                VehicleCondition.valueOf(doc.getString("condition") ?: VehicleCondition.GOOD.name)
            } catch (e: Exception) {
                VehicleCondition.GOOD
            },
            licenseImageUrl = doc.getString("licenseImageUrl"),
            vehicleImageUrl = doc.getString("vehicleImageUrl"),
            chassisImageUrl = doc.getString("chassisImageUrl"),
            engineImageUrl = doc.getString("engineImageUrl"),
            createdAt = doc.getDate("createdAt") ?: Date(),
            updatedAt = doc.getDate("updatedAt") ?: Date()
        )
    }
}


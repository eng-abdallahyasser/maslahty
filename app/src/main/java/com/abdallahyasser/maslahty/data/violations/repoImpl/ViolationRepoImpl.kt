package com.abdallahyasser.maslahty.data.violations.repoImpl

import com.abdallahyasser.maslahty.domain.common.Result
import com.example.maslahty.domain.entities.Violation
import com.example.maslahty.domain.entities.ViolationStatus
import com.abdallahyasser.maslahty.domain.violation.repo.ViolationRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import jakarta.inject.Inject
import kotlinx.coroutines.tasks.await
import java.util.Date

class ViolationRepoImpl @Inject constructor(
    private val authService: FirebaseAuth,
    private val firestoreService: FirebaseFirestore
) : ViolationRepository {

    companion object {
        private const val COLLECTION_VIOLATIONS = "violations"
    }

    override suspend fun getVehicleViolations(vehicleId: String): Result<List<Violation>> {
        return try {
            val query = firestoreService.collection(COLLECTION_VIOLATIONS)
                .whereEqualTo("vehicleId", vehicleId)
                .get()
                .await()

            val violations = query.documents.map { mapToViolation(it) }
            Result.Success(violations)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun getViolationById(id: String): Result<Violation> {
        return try {
            val document = firestoreService.collection(COLLECTION_VIOLATIONS)
                .document(id)
                .get()
                .await()

            if (document.exists()) {
                Result.Success(mapToViolation(document))
            } else {
                Result.Error(Exception("Violation not found with id: $id"))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun hasUnpaidViolations(vehicleId: String): Result<Boolean> {
        return try {
            val query = firestoreService.collection(COLLECTION_VIOLATIONS)
                .whereEqualTo("vehicleId", vehicleId)
                .whereEqualTo("status", ViolationStatus.UNPAID.name)
                .get()
                .await()

            Result.Success(query.documents.isNotEmpty())
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    private fun mapToViolation(doc: DocumentSnapshot): Violation {
        return Violation(
            id = doc.getString("id") ?: doc.id,
            vehicleId = doc.getString("vehicleId") ?: "",
            violationType = doc.getString("violationType") ?: "",
            description = doc.getString("description") ?: "",
            date = doc.getDate("date") ?: Date(),
            amount = doc.getDouble("amount") ?: 0.0,
            status = try {
                ViolationStatus.valueOf(doc.getString("status") ?: ViolationStatus.UNPAID.name)
            } catch (e: Exception) {
                ViolationStatus.UNPAID
            },
            location = doc.getString("location") ?: ""
        )
    }
}
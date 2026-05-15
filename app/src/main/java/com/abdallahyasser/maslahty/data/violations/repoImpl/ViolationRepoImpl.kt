package com.abdallahyasser.maslahty.data.violations.repoImpl

import com.abdallahyasser.maslahty.domain.common.Result
import com.example.maslahty.domain.entities.Violation
import com.example.maslahty.domain.repositories.ViolationRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import jakarta.inject.Inject

class ViolationRepoImpl @Inject constructor(
    private val authService: FirebaseAuth,
    private val firestoreService: FirebaseFirestore
): ViolationRepository {
    override suspend fun getVehicleViolations(vehicleId: String): Result<List<Violation>> {
        TODO("Not yet implemented")
    }

    override suspend fun getViolationById(id: String): Result<Violation> {
        TODO("Not yet implemented")
    }

    override suspend fun hasUnpaidViolations(vehicleId: String): Result<Boolean> {
        TODO("Not yet implemented")
    }
}
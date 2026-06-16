package com.abdallahyasser.maslahty.data.home

import com.abdallahyasser.maslahty.domain.home.entity.HomeUserData
import com.abdallahyasser.maslahty.domain.home.repository.HomeRepoInterface
import com.abdallahyasser.maslahty.domain.common.Result
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import jakarta.inject.Inject

class HomeRepoImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : HomeRepoInterface {

    override suspend fun getUserData(): Result<HomeUserData> {
        return try {
            val userId = auth.currentUser?.uid
                ?: return Result.Error(Exception("User not authenticated"))

            // Fetch user info for name
            val userDoc = firestore.collection("users")
                .document(userId)
                .get()
                .await()

            val fullName = userDoc.getString("fullName") ?: "زائر"

            // Fetch user's vehicles count
            val vehiclesQuery = firestore.collection("vehicle")
                .whereEqualTo("ownerId", userId)
                .get()
                .await()

            val vehiclesNumber = vehiclesQuery.size()

            // Fetch user's transfer requests as seller (sender)
            val sellerQuery = firestore.collection("transfer_requests")
                .whereEqualTo("sender_id", userId)
                .get()
                .await()

            // Fetch user's transfer requests as buyer (receiver)
            val buyerQuery = firestore.collection("transfer_requests")
                .whereEqualTo("receiver_id", userId)
                .get()
                .await()

            val allRequests = sellerQuery.documents + buyerQuery.documents
            val uniqueRequests = allRequests.distinctBy { it.id }

            val activeRequests = uniqueRequests.count {
                val status = it.getString("status") ?: ""
                status == "PENDING"
            }

            val completedRequests = uniqueRequests.count {
                val status = it.getString("status") ?: ""
                status == "APPROVED_BY_BUYER" || status == "COMPLETED"
            }

            Result.Success(
                HomeUserData(
                    fullName = fullName,
                    vehiclesNumber = vehiclesNumber,
                    activeRequests = activeRequests,
                    completedRequests = completedRequests
                )
            )
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}
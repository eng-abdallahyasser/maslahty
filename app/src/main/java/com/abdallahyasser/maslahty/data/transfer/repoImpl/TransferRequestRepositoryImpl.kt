package com.abdallahyasser.maslahty.data.transfer.repoImpl

import com.abdallahyasser.maslahty.domain.transfer.repo.TransferRequestRepository
import com.abdallahyasser.maslahty.domain.transfer.entity.TransferRequest
import com.abdallahyasser.maslahty.domain.transfer.entity.TransferStatus
import com.abdallahyasser.maslahty.domain.transfer.entity.PriceWarning
import com.abdallahyasser.maslahty.domain.common.Result
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.coroutines.tasks.await
import java.util.Date
import javax.inject.Inject

class TransferRequestRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
) : TransferRequestRepository {

    private val collection = firestore.collection("transfer_requests")

    override suspend fun createTransferRequest(request: TransferRequest): Result<TransferRequest> {
        return try {
            val buyerSnapshot = firestore.collection("users")
                .whereEqualTo("nationalId", request.buyerNationalId)
                .get()
                .await()
            val buyerDoc = buyerSnapshot.documents.firstOrNull()
            val buyerId = buyerDoc?.id
            val buyerName = buyerDoc?.getString("fullName") ?: request.buyerName

            val docRef = collection.document()
            val id = docRef.id
            val now = Date()
            
            val updatedRequest = request.copy(
                id = id,
                buyerId = buyerId,
                buyerName = buyerName,
                createdAt = now,
                updatedAt = now
            )

            val data = mapToFirestore(updatedRequest)
            docRef.set(data).await()

            Result.Success(updatedRequest)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun getTransferRequestById(id: String): Result<TransferRequest> {
        return try {
            val doc = collection.document(id).get().await()
            if (doc.exists()) {
                Result.Success(mapFromFirestore(doc))
            } else {
                Result.Error(Exception("Request not found"))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun getSellerRequests(sellerId: String): Result<List<TransferRequest>> {
        return try {
            val snapshot = collection.whereEqualTo("sender_id", sellerId).get().await()
            Result.Success(snapshot.documents.map { mapFromFirestore(it) })
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun getBuyerRequests(buyerId: String): Result<List<TransferRequest>> {
        return try {
            val snapshot = collection.whereEqualTo("receiver_id", buyerId).get().await()
            Result.Success(snapshot.documents.map { mapFromFirestore(it) })
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun updateTransferRequest(request: TransferRequest): Result<TransferRequest> {
        return try {
            val now = Date()
            val updatedRequest = request.copy(updatedAt = now)
            val data = mapToFirestore(updatedRequest)
            collection.document(request.id).set(data).await()
            Result.Success(updatedRequest)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun approveTransferRequest(requestId: String, buyerId: String): Result<TransferRequest> {
        return try {
            val now = Date()
            collection.document(requestId).update(
                "status", TransferStatus.APPROVED_BY_BUYER.name,
                "receiver_id", buyerId,
                "updated_at", now.time
            ).await()
            
            val doc = collection.document(requestId).get().await()
            Result.Success(mapFromFirestore(doc))
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun rejectTransferRequest(requestId: String, reason: String): Result<Unit> {
        return try {
            val now = Date()
            collection.document(requestId).update(
                "status", TransferStatus.REJECTED_BY_BUYER.name,
                "notes", reason, // Or we could have a separate field for rejection reason
                "updated_at", now.time
            ).await()
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    private fun mapToFirestore(request: TransferRequest): Map<String, Any?> {
        return mapOf(
            "id" to request.id,
            "vehicle_id" to request.vehicleId,
            "sender_id" to request.sellerId,
            "receiver_id" to request.buyerId,
            "buyer_national_id" to request.buyerNationalId,
            "price" to request.price,
            "status" to request.status.name,
            "seller_name" to request.sellerName,
            "buyer_name" to request.buyerName,
            "image_urls" to request.imageUrls,
            "created_at" to request.createdAt.time,
            "updated_at" to request.updatedAt.time,
            "notes" to request.notes,
            "price_warning" to request.priceWarning?.let {
                mapOf(
                    "market_price" to it.marketPrice,
                    "difference" to it.difference,
                    "percentage" to it.percentage,
                    "message" to it.message
                )
            }
        )
    }



    private fun mapFromFirestore(doc: DocumentSnapshot): TransferRequest {
        return TransferRequest(
            id = doc.getString("id") ?: doc.id,
            vehicleId = doc.getString("vehicle_id") ?: "",
            sellerId = doc.getString("sender_id") ?: "",
            buyerId = doc.getString("receiver_id"),
            buyerNationalId = doc.getString("buyer_national_id") ?: "",
            price = doc.getDouble("price") ?: 0.0,
            status = TransferStatus.valueOf(doc.getString("status") ?: TransferStatus.PENDING.name),
            sellerName = doc.getString("seller_name") ?: "",
            buyerName = doc.getString("buyer_name") ?: "",
            imageUrls = (doc.get("image_urls") as? List<*>)?.filterIsInstance<String>() ?: emptyList(),
            createdAt = Date(doc.getLong("created_at") ?: 0L),
            updatedAt = Date(doc.getLong("updated_at") ?: 0L),
            notes = doc.getString("notes") ?: "",
            priceWarning = (doc.get("price_warning") as? Map<*, *>)?.let {
                PriceWarning(
                    marketPrice = (it["market_price"] as? Number)?.toDouble() ?: 0.0,
                    difference = (it["difference"] as? Number)?.toDouble() ?: 0.0,
                    percentage = (it["percentage"] as? Number)?.toDouble() ?: 0.0,
                    message = it["message"] as? String ?: ""
                )
            }
        )
    }
}
package com.example.maslahty.domain.entities

data class TransferRequest(
    val id: String,
    val vehicleId: String,
    val sellerId: String,
    val buyerId: String,
    val price: Double,
    val status: TransferStatus,
    val sellerName: String,
    val buyerName: String,
    val createdAt: Long,
    val updatedAt: Long,
    val notes: String = "",
    val priceWarning: PriceWarning? = null
)

enum class TransferStatus {
    PENDING, APPROVED_BY_BUYER, REJECTED_BY_BUYER, COMPLETED, CANCELLED
}

data class PriceWarning(
    val marketPrice: Double,
    val difference: Double,
    val percentage: Double,
    val message: String
)


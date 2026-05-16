package com.abdallahyasser.maslahty.domain.transfer.entity

import java.util.Date

data class TransferRequest(
    val id: String,
    val vehicleId: String,
    val sellerId: String,
    val buyerId: String? = null,
    val buyerNationalId: String,
    val buyerName: String,
    val sellerName: String,
    val price: Double,
    val status: TransferStatus,
    val imageUrls: List<String> = emptyList(),
    val notes: String = "",
    val priceWarning: PriceWarning? = null,
    val createdAt: Date,
    val updatedAt: Date
)


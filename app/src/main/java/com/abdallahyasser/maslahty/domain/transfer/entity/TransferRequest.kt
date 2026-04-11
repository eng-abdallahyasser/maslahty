package com.abdallahyasser.maslahty.domain.transfer.entity

import java.util.Date

data class TransferRequest(
    val id: String,
    val vehicleId: String,
    val sellerId: String,
    val buyerId: String,
    val price: Double,
    val status: TransferStatus,
    val sellerName: String,
    val buyerName: String,
    val createdAt: Date,
    val updatedAt: Date,
    val notes: String = "",
    val priceWarning: PriceWarning? = null
)

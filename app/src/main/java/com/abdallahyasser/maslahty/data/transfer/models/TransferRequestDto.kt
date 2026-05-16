package com.abdallahyasser.maslahty.data.transfer.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TransferRequestDto(
    @SerialName("id")
    val id: String = "",

    @SerialName("vehicle_id")
    val vehicleId: String = "",

    @SerialName("sender_id")
    val senderId: String = "",

    @SerialName("receiver_id")
    val receiverId: String? = null,

    @SerialName("buyer_national_id")
    val buyerNationalId: String = "",

    @SerialName("price")
    val price: Double = 0.0,

    @SerialName("status")
    val status: String = "",

    @SerialName("seller_name")
    val sellerName: String = "",

    @SerialName("buyer_name")
    val buyerName: String = "",

    @SerialName("image_urls")
    val imageUrls: List<String> = emptyList(),

    @SerialName("created_at")
    val createdAt: Long = 0,

    @SerialName("updated_at")
    val updatedAt: Long = 0,

    @SerialName("notes")
    val notes: String = "",

    @SerialName("price_warning")
    val priceWarning: PriceWarningDto? = null
)
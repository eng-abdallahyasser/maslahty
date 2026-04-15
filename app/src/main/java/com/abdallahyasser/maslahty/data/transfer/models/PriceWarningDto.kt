package com.abdallahyasser.maslahty.data.transfer.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PriceWarningDto(
    @SerialName("market_price")
    val marketPrice: Double,
    @SerialName("difference")
    val difference: Double,
    @SerialName("percentage")
    val percentage: Double,
    @SerialName("message")
    val message: String

)

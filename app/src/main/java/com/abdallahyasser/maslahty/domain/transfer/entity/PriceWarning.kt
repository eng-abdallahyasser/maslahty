package com.abdallahyasser.maslahty.domain.transfer.entity

data class PriceWarning(
    val marketPrice: Double,
    val difference: Double,
    val percentage: Double,
    val message: String
)

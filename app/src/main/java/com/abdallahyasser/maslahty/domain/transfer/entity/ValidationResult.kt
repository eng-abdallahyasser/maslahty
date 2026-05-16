package com.abdallahyasser.maslahty.domain.transfer.entity

data class ValidationResult(
    val successful: Boolean,
    val errorMessage: String? = null
)

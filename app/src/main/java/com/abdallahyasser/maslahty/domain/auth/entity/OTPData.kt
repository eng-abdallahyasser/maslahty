package com.abdallahyasser.maslahty.domain.auth.entity

data class OTPData(
    val phoneNumber: String,
    val code: String,
    val expiryTime: Long,
    val attempts: Int = 0
)
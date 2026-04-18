package com.abdallahyasser.maslahty.domain.auth.entity

data class User(
    val id: String,
    val fullName: String,
    val nationalId: String,
    val email: String,
    val phoneNumber: String,
    val password: String
)

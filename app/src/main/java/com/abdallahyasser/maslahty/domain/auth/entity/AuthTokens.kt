package com.abdallahyasser.maslahty.domain.auth.entity

data class AuthTokens(
    val accessToken: String,
    val refreshToken: String,
    val expiresIn: Long
)
package com.abdallahyasser.maslahty.domain.auth.repo

import com.abdallahyasser.maslahty.domain.auth.entity.AuthTokens
import com.abdallahyasser.maslahty.domain.auth.entity.OTPData
import com.abdallahyasser.maslahty.domain.home.entity.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun sendOTP(nationalId: String, phoneNumber: String): Result<OTPData>
    suspend fun verifyOTP(phoneNumber: String, code: String): Result<AuthTokens>
    suspend fun registerUser(user: User): Result<User>
    suspend fun logout(): Result<Unit>
    fun isLoggedIn(): Flow<Boolean>
    suspend fun getCurrentUser(): Result<User>
}


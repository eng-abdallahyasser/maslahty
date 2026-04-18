package com.abdallahyasser.maslahty.domain.auth.repo

import com.abdallahyasser.maslahty.domain.auth.entity.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun registerUser(user: User): Result<User>
    suspend fun logout(): Result<Unit>
    fun isLoggedIn(): Boolean
    suspend fun getCurrentUser(): Result<User>
    suspend fun login(phoneNumber: String, password: String): Result<User>
    suspend fun sendOtp(phoneNumber: String): Result<Unit>
    suspend fun verifyOtp(phoneNumber: String, otp: String): Result<User>
}


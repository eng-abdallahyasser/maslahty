package com.abdallahyasser.maslahty.domain.auth.repo

import com.abdallahyasser.maslahty.domain.auth.entity.AuthResult
import com.abdallahyasser.maslahty.domain.auth.entity.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun registerUser(user: User): AuthResult<User>
    suspend fun logout(): Result<Unit>
    fun isLoggedIn(): Boolean
    suspend fun getCurrentUser(): com.abdallahyasser.maslahty.domain.common.Result<User>
    suspend fun login(nationalIdOrEmail: String, password: String): AuthResult<User>
    suspend fun sendOtp(phoneNumber: String): Result<Unit>
    suspend fun verifyOtp(phoneNumber: String, otp: String): Result<User>
    suspend fun resendVerificationEmail(): Result<Unit>
    suspend fun resendVerificationEmail(email: String, password: String): Result<Unit>
}



package com.abdallahyasser.maslahty.domain.auth.repo

import com.abdallahyasser.maslahty.domain.auth.entity.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun registerUser(user: com.abdallahyasser.maslahty.domain.auth.entity.User): Result<com.abdallahyasser.maslahty.domain.auth.entity.User>
    suspend fun logout(): Result<Unit>
    fun isLoggedIn(): Flow<Boolean>
    suspend fun getCurrentUser(): Result<User>
}


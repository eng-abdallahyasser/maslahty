package com.abdallahyasser.maslahty.data.repoImpl

import com.abdallahyasser.maslahty.domain.auth.entity.User
import com.abdallahyasser.maslahty.domain.auth.repo.AuthRepository

class AuthRepositoryImpl : AuthRepository {

    // Mock user storage
    private val users = mutableListOf<User>()
    private var currentUser: User? = null

    override suspend fun registerUser(user: User): Result<User> {
        return try {
            val newUser = user.copy(id = "user_${System.currentTimeMillis()}")
            users.add(newUser)
            currentUser = newUser
            Result.success(newUser)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun logout(): Result<Unit> {
        return try {
            currentUser = null
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun isLoggedIn(): Boolean {
        return currentUser != null
    }

    override suspend fun getCurrentUser(): Result<User> {
        return currentUser?.let {
            Result.success(it)
        } ?: Result.failure(Exception("No user logged in"))
    }

    override suspend fun login(phoneNumber: String, password: String): Result<User> {
        return try {
            // Mock login - create a user for demo purposes
            val user = User(
                id = "user_${System.currentTimeMillis()}",
                fullName = "مستخدم تجريبي",
                nationalId = phoneNumber,
                email = "",
                phoneNumber = password,
                password = password
            )
            currentUser = user
            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun sendOtp(phoneNumber: String): Result<Unit> {
        return try {
            // Mock OTP sending
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun verifyOtp(phoneNumber: String, otp: String): Result<User> {
        return try {
            // Mock OTP verification
            val user = User(
                id = "user_${System.currentTimeMillis()}",
                fullName = "مستخدم تجريبي",
                nationalId = "",
                email = "",
                phoneNumber = phoneNumber,
                password = ""
            )
            currentUser = user
            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

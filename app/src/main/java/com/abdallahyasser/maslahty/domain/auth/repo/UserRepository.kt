package com.abdallahyasser.maslahty.domain.auth.repo

import com.abdallahyasser.maslahty.domain.auth.entity.User

interface UserRepository {
        suspend fun getUserById(userId: String): Result<User>
        suspend fun getUserByNationalId(userId: String): Result<User>
        suspend fun createUser(user: User): Result<User>
        suspend fun updateUser(user: User): Result<User>
        suspend fun deleteUser(userId: String): Result<Unit>
}
package com.abdallahyasser.maslahty.data.auth

import com.abdallahyasser.maslahty.domain.auth.entity.User
import com.abdallahyasser.maslahty.domain.auth.repo.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await

class AuthRepositoryImpl @Inject constructor(
    val authService: FirebaseAuth,
    val firestoreService: FirebaseFirestore
): AuthRepository {

    override suspend fun registerUser(user: User): Result<User> {
        try {

            val result = authService.createUserWithEmailAndPassword(user.email, user.nationalId).await()
            val uid = result.user?.uid
            if (uid!=null) {
                firestoreService.collection("users")
                    .document(uid)
                    .set(user.copy(id = uid))
                    .await()
            }
            return Result.success(user)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

    override suspend fun logout(): Result<Unit> {
        TODO("Not yet implemented")
    }

    override fun isLoggedIn(): Flow<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun getCurrentUser(): Result<User> {
        TODO("Not yet implemented")
    }
}
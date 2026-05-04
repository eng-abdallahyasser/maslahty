package com.abdallahyasser.maslahty.data.auth

import android.app.Activity
import android.util.Log
import com.abdallahyasser.maslahty.domain.auth.entity.User
import com.abdallahyasser.maslahty.domain.auth.repo.AuthRepository
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import java.util.concurrent.TimeUnit
import kotlin.coroutines.resume

class AuthRepositoryImpl @Inject constructor(
    private val authService: FirebaseAuth,
    private val firestoreService: FirebaseFirestore
): AuthRepository {

    private var verificationId: String? = null

    override suspend fun registerUser(user: User): Result<User> {
        return try {
            val result = authService.createUserWithEmailAndPassword(user.email, user.nationalId).await()
            val uid = result.user?.uid ?: throw Exception("Registration failed")
            val updatedUser = user.copy(id = uid)
            firestoreService.collection("users")
                .document(uid)
                .set(updatedUser)
                .await()
            Result.success(updatedUser)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun logout(): Result<Unit> {
        return try {
            authService.signOut()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun isLoggedIn(): Boolean {
        return authService.currentUser != null
    }

    override suspend fun getCurrentUser(): Result<User> {
        return try {
            val uid = authService.currentUser?.uid
            if (uid != null) {
                val document = firestoreService.collection("users").document(uid).get().await()
                if (document.exists()) {
                    val user = User(
                        id = document.getString("id") ?: uid,
                        fullName = document.getString("fullName") ?: "",
                        nationalId = document.getString("nationalId") ?: "",
                        email = document.getString("email") ?: "",
                        phoneNumber = document.getString("phoneNumber") ?: "",
                        password = document.getString("password") ?: ""
                    )
                    Result.success(user)
                } else {
                    Result.failure(Exception("User data not found in Firestore"))
                }
            } else {
                Result.failure(Exception("No user currently logged in"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun login(
        phoneNumber: String,
        password: String
    ): Result<User> {
        return try {
            // Note: In Firebase Auth, we usually use email and password.
            // If phoneNumber is passed here, we treat it as the email/identifier.
            val result = authService.signInWithEmailAndPassword(phoneNumber, password).await()
            val uid = result.user?.uid ?: throw Exception("Login failed")
            val document = firestoreService.collection("users").document(uid).get().await()
            if (document.exists()) {
                val user = User(
                    id = document.getString("id") ?: uid,
                    fullName = document.getString("fullName") ?: "",
                    nationalId = document.getString("nationalId") ?: "",
                    email = document.getString("email") ?: "",
                    phoneNumber = document.getString("phoneNumber") ?: "",
                    password = document.getString("password") ?: ""
                )
                Result.success(user)
            } else {
                // If user exists in Auth but not Firestore, return skeleton
                throw Exception("User data not exists")
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun sendOtp(phoneNumber: String): Result<Unit> = suspendCancellableCoroutine { continuation ->
        val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                // This is called when verification is done automatically (e.g. via instant verification or auto-retrieval)
            }

            override fun onVerificationFailed(e: FirebaseException) {
                if (continuation.isActive) {
                    continuation.resume(Result.failure(e))
                }
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                this@AuthRepositoryImpl.verificationId = verificationId
                if (continuation.isActive) {
                    continuation.resume(Result.success(Unit))
                }
            }
        }

        val options = PhoneAuthOptions.newBuilder(authService)
            .setPhoneNumber(phoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setCallbacks(callbacks)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    override suspend fun verifyOtp(
        phoneNumber: String,
        otp: String
    ): Result<User> {

        
        val vid = verificationId ?: return Result.failure(Exception("Verification ID not found. Call sendOtp first."))
        return try {
            val credential = PhoneAuthProvider.getCredential(vid, otp)
            val result = authService.signInWithCredential(credential).await()
            val uid = result.user?.uid ?: throw Exception("OTP verification failed")
            
            val document = firestoreService.collection("users").document(uid).get().await()
            if (document.exists()) {
                val user = User(
                    id = document.getString("id") ?: uid,
                    fullName = document.getString("fullName") ?: "",
                    nationalId = document.getString("nationalId") ?: "",
                    email = document.getString("email") ?: "",
                    phoneNumber = document.getString("phoneNumber") ?: "",
                    password = document.getString("password") ?: ""
                )
                Result.success(user)
            } else {
                // Create user in Firestore if they don't exist
                val newUser = User(uid, "", "", "", phoneNumber, "")
                firestoreService.collection("users").document(uid).set(newUser).await()
                Result.success(newUser)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

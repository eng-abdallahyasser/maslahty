package com.abdallahyasser.maslahty.data.auth

import com.abdallahyasser.maslahty.domain.auth.entity.AuthResult
import com.abdallahyasser.maslahty.domain.auth.entity.User
import com.abdallahyasser.maslahty.domain.auth.repo.AuthRepository
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import jakarta.inject.Inject
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import java.util.concurrent.TimeUnit
import kotlin.coroutines.resume

import com.abdallahyasser.maslahty.util.PreferenceManager

class AuthRepositoryImpl @Inject constructor(
    private val authService: FirebaseAuth,
    private val firestoreService: FirebaseFirestore
): AuthRepository {

    private var verificationId: String? = null

    override suspend fun registerUser(user: User): AuthResult<User> {
        return try {
            // Check if national ID already exists
            val query = firestoreService.collection("users")
                .whereEqualTo("nationalId", user.nationalId)
                .get()
                .await()

            if (!query.isEmpty) {
                return AuthResult.Error("الرقم القومي مسجل بالفعل")
            }

            val result = authService.createUserWithEmailAndPassword(user.email, user.password).await()
            val firebaseUser = result.user ?: throw Exception("Failing to retrieve registered user")
            val uid = firebaseUser.uid
            val updatedUser = user.copy(id = uid)

            // Save user to Firestore while still signed in
            firestoreService.collection("users")
                .document(uid)
                .set(updatedUser)
                .await()

            // Send verification email before sign out
            try {
                firebaseUser.sendEmailVerification().await()
            } catch (mailEx: Throwable) {
                authService.signOut()
                return AuthResult.Error("تم إنشاء الحساب ولكن فشل إرسال بريد التحقق: ${mailEx.message}")
            }

            authService.signOut()
            AuthResult.Success(updatedUser)
        } catch (e: Throwable) {
            try {
                authService.signOut()
            } catch (_: Throwable) {}
            AuthResult.Error(e.message ?: "فشل إنشاء الحساب")
        }
    }

    override suspend fun logout(): Result<Unit> {
        return try {
            authService.signOut()
            PreferenceManager.clearUser()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun isLoggedIn(): Boolean {
        return authService.currentUser != null
    }

    override suspend fun getCurrentUser(): com.abdallahyasser.maslahty.domain.common.Result<User> {
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
                    com.abdallahyasser.maslahty.domain.common.Result.Success(user)
                } else {
                    com.abdallahyasser.maslahty.domain.common.Result.Error(Exception("User data not found in Firestore"))
                }
            } else {
                com.abdallahyasser.maslahty.domain.common.Result.Error(Exception("No user currently logged in"))
            }
        } catch (e: Exception) {
            com.abdallahyasser.maslahty.domain.common.Result.Error(e)
        }
    }

    override suspend fun login(
        nationalIdOrEmail: String,
        password: String
    ): AuthResult<User> {
        return try {
            val email = if (!nationalIdOrEmail.contains("@")) {
                val query = firestoreService.collection("users")
                    .whereEqualTo("nationalId", nationalIdOrEmail)
                    .get()
                    .await()
                if (query.isEmpty) {
                    return AuthResult.Error("الرقم القومي غير مسجل")
                }
                query.documents.first().getString("email") ?: return AuthResult.Error("البريد الإلكتروني غير موجود")
            } else {
                nationalIdOrEmail
            }

            val result = authService.signInWithEmailAndPassword(email, password).await()
            val firebaseUser = result.user ?: throw Exception("Login failed")

            // Reload user to refresh email verification status from server
            firebaseUser.reload().await()

            if (!firebaseUser.isEmailVerified) {
                authService.signOut()
                return AuthResult.EmailNotVerified
            }

            val uid = firebaseUser.uid
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
                AuthResult.Success(user)
            } else {
                authService.signOut()
                AuthResult.Error("بيانات المستخدم غير موجودة")
            }
        } catch (e: Throwable) {
            try {
                authService.signOut()
            } catch (_: Throwable) {}
            AuthResult.Error(e.message ?: "فشل تسجيل الدخول")
        }
    }

    override suspend fun sendOtp(phoneNumber: String): Result<Unit> {
         return Result.failure(Exception("sendOtp : Not yet implemented"))
    }

    override suspend fun verifyOtp(
        phoneNumber: String,
        otp: String
    ): Result<User> {
        return Result.failure(Exception("verifyOtp : Not yet implemented"))
    }

//    override suspend fun sendOtp(phoneNumber: String): Result<Unit> = suspendCancellableCoroutine { continuation ->
//        val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//
//            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
//                // This is called when verification is done automatically (e.g. via instant verification or auto-retrieval)
//            }
//
//            override fun onVerificationFailed(e: FirebaseException) {
//                if (continuation.isActive) {
//                    continuation.resume(Result.failure(e))
//                }
//            }
//
//            override fun onCodeSent(
//                verificationId: String,
//                token: PhoneAuthProvider.ForceResendingToken
//            ) {
//                this@AuthRepositoryImpl.verificationId = verificationId
//                if (continuation.isActive) {
//                    continuation.resume(Result.success(Unit))
//                }
//            }
//        }
//
//        val options = PhoneAuthOptions.newBuilder(authService)
//            .setPhoneNumber(phoneNumber)
//            .setTimeout(60L, TimeUnit.SECONDS)
//            .setCallbacks(callbacks)
//            .build()
//        PhoneAuthProvider.verifyPhoneNumber(options)
//    }
//
//    override suspend fun verifyOtp(
//        phoneNumber: String,
//        otp: String
//    ): Result<User> {
//
//
//        val vid = verificationId ?: return Result.failure(Exception("Verification ID not found. Call sendOtp first."))
//        return try {
//            val credential = PhoneAuthProvider.getCredential(vid, otp)
//            val result = authService.signInWithCredential(credential).await()
//            val uid = result.user?.uid ?: throw Exception("OTP verification failed")
//
//            val document = firestoreService.collection("users").document(uid).get().await()
//            if (document.exists()) {
//                val user = User(
//                    id = document.getString("id") ?: uid,
//                    fullName = document.getString("fullName") ?: "",
//                    nationalId = document.getString("nationalId") ?: "",
//                    email = document.getString("email") ?: "",
//                    phoneNumber = document.getString("phoneNumber") ?: "",
//                    password = document.getString("password") ?: ""
//                )
//                Result.success(user)
//            } else {
//                // Create user in Firestore if they don't exist
//                val newUser = User(uid, "", "", "", phoneNumber, "")
//                firestoreService.collection("users").document(uid).set(newUser).await()
//                Result.success(newUser)
//            }
//        } catch (e: Exception) {
//            Result.failure(e)
//        }
//    }

    override suspend fun resendVerificationEmail(): Result<Unit> {
        return try {
            val user = authService.currentUser ?: throw Exception("No user currently signed in")
            user.sendEmailVerification().await()
            Result.success(Unit)
        } catch (e: Throwable) {
            Result.failure(Exception(e.message ?: "فشل إعادة إرسال البريد"))
        }
    }

    override suspend fun resendVerificationEmail(email: String, password: String): Result<Unit> {
        return try {
            val resolvedEmail = if (!email.contains("@")) {
                val query = firestoreService.collection("users")
                    .whereEqualTo("nationalId", email)
                    .get()
                    .await()
                if (query.isEmpty) {
                    throw Exception("الرقم القومي غير مسجل")
                }
                query.documents.first().getString("email") ?: throw Exception("البريد الإلكتروني غير موجود")
            } else {
                email
            }

            val result = authService.signInWithEmailAndPassword(resolvedEmail, password).await()
            val user = result.user ?: throw Exception("Failed to sign in for resending verification email")
            user.sendEmailVerification().await()
            authService.signOut()
            Result.success(Unit)
        } catch (e: Throwable) {
            try {
                authService.signOut()
            } catch (_: Throwable) {}
            Result.failure(Exception(e.message ?: "فشل إعادة إرسال البريد"))
        }
    }
}

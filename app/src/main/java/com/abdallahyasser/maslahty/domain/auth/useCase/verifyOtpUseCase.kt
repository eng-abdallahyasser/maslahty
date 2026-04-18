package com.abdallahyasser.maslahty.domain.auth.useCase

import com.abdallahyasser.maslahty.domain.auth.entity.User
import com.abdallahyasser.maslahty.domain.auth.repo.AuthRepository

class verifyOtpUseCase(private val authRepository: AuthRepository) {
    suspend operator fun invoke(phoneNumber: String, otp: String): Result<User> {
        return authRepository.verifyOtp(phoneNumber, otp)
    }
}
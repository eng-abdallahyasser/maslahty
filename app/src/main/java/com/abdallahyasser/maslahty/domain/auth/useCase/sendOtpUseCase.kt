package com.abdallahyasser.maslahty.domain.auth.useCase

import com.abdallahyasser.maslahty.domain.auth.repo.AuthRepository

class sendOtpUseCase(private val authRepository: AuthRepository){

    suspend operator fun invoke(phoneNumber: String): Result<Unit> {
        return authRepository.sendOtp(phoneNumber)
    }
}
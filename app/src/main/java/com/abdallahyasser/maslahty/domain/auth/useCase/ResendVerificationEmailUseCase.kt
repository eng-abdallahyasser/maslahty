package com.abdallahyasser.maslahty.domain.auth.useCase

import com.abdallahyasser.maslahty.domain.auth.repo.AuthRepository

class ResendVerificationEmailUseCase(private val repo: AuthRepository) {
    suspend operator fun invoke(): Result<Unit> {
        return repo.resendVerificationEmail()
    }

    suspend operator fun invoke(email: String, password: String): Result<Unit> {
        return repo.resendVerificationEmail(email, password)
    }
}

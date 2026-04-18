package com.abdallahyasser.maslahty.domain.auth.useCase

import com.abdallahyasser.maslahty.domain.auth.repo.AuthRepository

class isLoggedInUseCase(private val authRepository: AuthRepository){

    suspend operator fun invoke(): Boolean {
        return authRepository.isLoggedIn()
    }
}
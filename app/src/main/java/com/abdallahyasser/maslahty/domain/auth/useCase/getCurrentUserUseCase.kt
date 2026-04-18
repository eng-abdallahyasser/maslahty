package com.abdallahyasser.maslahty.domain.auth.useCase

import com.abdallahyasser.maslahty.domain.auth.entity.User
import com.abdallahyasser.maslahty.domain.auth.repo.AuthRepository

class getCurrentUserUseCase(private val authRepository: AuthRepository){
    suspend operator fun invoke(): Result<User> {
        return authRepository.getCurrentUser()
    }
}
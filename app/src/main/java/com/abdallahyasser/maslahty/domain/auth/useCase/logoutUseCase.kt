package com.abdallahyasser.maslahty.domain.auth.useCase

import com.abdallahyasser.maslahty.domain.auth.entity.User
import com.abdallahyasser.maslahty.domain.auth.repo.AuthRepository

class logoutUseCase (private val authRepository: AuthRepository){
    suspend operator fun invoke(): Result<Unit> {
        return authRepository.logout()
    }

}
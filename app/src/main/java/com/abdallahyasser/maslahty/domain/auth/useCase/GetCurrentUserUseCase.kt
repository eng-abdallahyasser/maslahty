package com.abdallahyasser.maslahty.domain.auth.useCase

import com.abdallahyasser.maslahty.domain.auth.entity.User
import com.abdallahyasser.maslahty.domain.auth.repo.AuthRepository
import com.abdallahyasser.maslahty.domain.common.Result

class GetCurrentUserUseCase(private val authRepository: AuthRepository){
    suspend operator fun invoke(): Result<User> {
        return authRepository.getCurrentUser()
    }
}
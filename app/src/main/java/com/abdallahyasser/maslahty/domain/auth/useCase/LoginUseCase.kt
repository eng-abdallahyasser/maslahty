package com.abdallahyasser.maslahty.domain.auth.useCase

import com.abdallahyasser.maslahty.domain.auth.entity.AuthResult
import com.abdallahyasser.maslahty.domain.auth.entity.User
import com.abdallahyasser.maslahty.domain.auth.repo.AuthRepository

class LoginUseCase(private val authRepository: AuthRepository){

    suspend operator fun invoke(nationalIdOrEmail: String, password: String): AuthResult<User> {
        return authRepository.login(nationalIdOrEmail= nationalIdOrEmail, password= password)
    }
}
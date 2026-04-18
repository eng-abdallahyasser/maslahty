package com.abdallahyasser.maslahty.domain.auth.useCase

import com.abdallahyasser.maslahty.domain.auth.entity.User
import com.abdallahyasser.maslahty.domain.auth.repo.AuthRepository

class loginUseCase(private val authRepository: AuthRepository){

    suspend operator fun invoke(username: String, password: String): Result<User> {
        return authRepository.login(username, password)
    }
}
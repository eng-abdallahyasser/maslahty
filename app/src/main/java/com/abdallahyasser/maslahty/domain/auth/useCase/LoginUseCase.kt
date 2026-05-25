package com.abdallahyasser.maslahty.domain.auth.useCase

import com.abdallahyasser.maslahty.data.auth.AuthRepositoryImpl
import com.abdallahyasser.maslahty.domain.auth.entity.User

class LoginUseCase(private val authRepository: AuthRepositoryImpl){

    suspend operator fun invoke(nationalIdOrEmail: String, password: String): Result<User> {
        return authRepository.login(nationalIdOrEmail= nationalIdOrEmail, password= password)
    }
}
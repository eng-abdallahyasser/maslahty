package com.abdallahyasser.maslahty.domain.auth.useCase


import com.abdallahyasser.maslahty.domain.auth.entity.User
import com.abdallahyasser.maslahty.domain.auth.repo.AuthRepository


class RegisterUserUseCase (private val repo: AuthRepository) {
    suspend operator fun invoke(user: User): Result<User> {
        return repo.registerUser(user)
    }
}
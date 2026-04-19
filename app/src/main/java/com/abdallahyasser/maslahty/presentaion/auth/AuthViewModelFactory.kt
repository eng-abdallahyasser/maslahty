package com.abdallahyasser.maslahty.presentaion.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.abdallahyasser.maslahty.data.repoImpl.AuthRepositoryImpl
import com.abdallahyasser.maslahty.domain.auth.useCase.RegisterUserUseCase
import com.abdallahyasser.maslahty.domain.auth.useCase.getCurrentUserUseCase
import com.abdallahyasser.maslahty.domain.auth.useCase.isLoggedInUseCase
import com.abdallahyasser.maslahty.domain.auth.useCase.loginUseCase
import com.abdallahyasser.maslahty.domain.auth.useCase.logoutUseCase
import com.abdallahyasser.maslahty.domain.auth.useCase.sendOtpUseCase
import com.abdallahyasser.maslahty.domain.auth.useCase.verifyOtpUseCase

class AuthViewModelFactory : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            val repository = AuthRepositoryImpl()
            return AuthViewModel(
                loginUseCase = loginUseCase(repository),
                registerUseCase = RegisterUserUseCase(repository),
                logoutUseCase = logoutUseCase(repository),
                getCurrentUserUseCase = getCurrentUserUseCase(repository),
                sendOtpUseCase = sendOtpUseCase(repository),
                verifyOtpUseCase = verifyOtpUseCase(repository),
                isLoggedInUseCase = isLoggedInUseCase(repository)
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}

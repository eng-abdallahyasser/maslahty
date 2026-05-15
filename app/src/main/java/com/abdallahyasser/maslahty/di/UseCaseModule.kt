package com.abdallahyasser.maslahty.di

import com.abdallahyasser.maslahty.data.auth.AuthRepositoryImpl
import com.abdallahyasser.maslahty.data.transfer.repoImpl.VehicleRepositoryImpl
import com.abdallahyasser.maslahty.data.violations.repoImpl.ViolationRepoImpl
import com.abdallahyasser.maslahty.domain.auth.repo.AuthRepository
import com.abdallahyasser.maslahty.domain.auth.useCase.LoginUseCase
import com.abdallahyasser.maslahty.domain.auth.useCase.RegisterUserUseCase
import com.abdallahyasser.maslahty.domain.auth.useCase.getCurrentUserUseCase
import com.abdallahyasser.maslahty.domain.auth.useCase.isLoggedInUseCase
import com.abdallahyasser.maslahty.domain.auth.useCase.logoutUseCase
import com.abdallahyasser.maslahty.domain.auth.useCase.sendOtpUseCase
import com.abdallahyasser.maslahty.domain.auth.useCase.verifyOtpUseCase
import com.abdallahyasser.maslahty.domain.violation.usecase.GetUserVehiclesUseCase
import com.example.maslahty.domain.usecases.violation.CheckViolationsForTransferUseCase
import com.example.maslahty.domain.usecases.violation.GetVehicleViolationsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideLoginUseCase(authRepository: AuthRepositoryImpl): LoginUseCase {
        return LoginUseCase(authRepository)
    }

    @Provides
    @Singleton
    fun provideRegisterUseCase(authRepository: AuthRepository): RegisterUserUseCase {
        return RegisterUserUseCase(authRepository)

    }

    @Provides
    @Singleton
    fun provideLogoutUseCase(authRepository: AuthRepositoryImpl): logoutUseCase {
        return logoutUseCase(authRepository)
    }
    @Provides
    @Singleton
    fun provideCurrentUserUseCase(authRepository: AuthRepositoryImpl): getCurrentUserUseCase {
        return getCurrentUserUseCase(authRepository)
    }@Provides
    @Singleton
    fun provideSendOtpUseCase(authRepository: AuthRepositoryImpl): sendOtpUseCase {
        return sendOtpUseCase(authRepository)
    }
    @Provides
    @Singleton
    fun provideVerifyOtpUseCase(authRepository: AuthRepositoryImpl): verifyOtpUseCase {
        return verifyOtpUseCase(authRepository)
    }


    @Provides
    @Singleton
    fun provideIsLoggedInUseCase(authRepository: AuthRepositoryImpl): isLoggedInUseCase {
        return isLoggedInUseCase(authRepository)
    }

    @Provides
    @Singleton
    fun provideGetUserVehiclesUseCase(violationRepository: VehicleRepositoryImpl): GetUserVehiclesUseCase {
        return GetUserVehiclesUseCase(violationRepository)
    }


    @Provides
    @Singleton
    fun provideCheckViolationsForTransferUseCase(violationRepository: ViolationRepoImpl): CheckViolationsForTransferUseCase {
        return CheckViolationsForTransferUseCase(violationRepository)
    }


    @Provides
    @Singleton
    fun provideGetVehicleViolationsUseCase(violationRepository: ViolationRepoImpl): GetVehicleViolationsUseCase {
        return GetVehicleViolationsUseCase(violationRepository)

    }
}
package com.abdallahyasser.maslahty.di

import com.abdallahyasser.maslahty.data.auth.AuthRepositoryImpl
import com.abdallahyasser.maslahty.data.vehicle.repoImpl.VehicleRepositoryImpl
import com.abdallahyasser.maslahty.data.violations.repoImpl.ViolationRepoImpl
import com.abdallahyasser.maslahty.domain.auth.repo.AuthRepository
import com.abdallahyasser.maslahty.domain.transfer.repo.TransferRequestRepository
import com.abdallahyasser.maslahty.domain.transfer.usecase.*
import com.abdallahyasser.maslahty.domain.auth.useCase.LoginUseCase
import com.abdallahyasser.maslahty.domain.auth.useCase.RegisterUserUseCase
import com.abdallahyasser.maslahty.domain.auth.useCase.GetCurrentUserUseCase
import com.abdallahyasser.maslahty.domain.auth.useCase.isLoggedInUseCase
import com.abdallahyasser.maslahty.domain.auth.useCase.logoutUseCase
import com.abdallahyasser.maslahty.domain.auth.useCase.sendOtpUseCase
import com.abdallahyasser.maslahty.domain.auth.useCase.verifyOtpUseCase
import com.abdallahyasser.maslahty.domain.auth.useCase.ResendVerificationEmailUseCase
import com.abdallahyasser.maslahty.domain.requests.ApproveTransferRequestUseCase
import com.abdallahyasser.maslahty.domain.transfer.usecase.CreateTransferRequestUseCase
import com.abdallahyasser.maslahty.domain.requests.GetBuyerRequestsUseCase
import com.abdallahyasser.maslahty.domain.requests.RejectTransferRequestUseCase
import com.abdallahyasser.maslahty.domain.vehicle.usecase.GetUserVehiclesUseCase
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
    fun provideLoginUseCase(authRepository: AuthRepository): LoginUseCase {
        return LoginUseCase(authRepository)
    }

    @Provides
    @Singleton
    fun provideRegisterUseCase(authRepository: AuthRepository): RegisterUserUseCase {
        return RegisterUserUseCase(authRepository)
    }

    @Provides
    @Singleton
    fun provideResendVerificationEmailUseCase(authRepository: AuthRepository): ResendVerificationEmailUseCase {
        return ResendVerificationEmailUseCase(authRepository)
    }

    @Provides
    @Singleton
    fun provideLogoutUseCase(authRepository: AuthRepositoryImpl): logoutUseCase {
        return logoutUseCase(authRepository)
    }
    @Provides
    @Singleton
    fun provideCurrentUserUseCase(authRepository: AuthRepositoryImpl): GetCurrentUserUseCase {
        return GetCurrentUserUseCase(authRepository)
    }
    @Provides
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

    @Provides
    @Singleton
    fun provideValidateNationalIdUseCase(): ValidateNationalIdUseCase {
        return ValidateNationalIdUseCase()
    }

    @Provides
    @Singleton
    fun provideValidatePriceUseCase(): ValidatePriceUseCase {
        return ValidatePriceUseCase()
    }

    @Provides
    @Singleton
    fun provideValidateTransferImagesUseCase(): ValidateTransferImagesUseCase {
        return ValidateTransferImagesUseCase()
    }

    @Provides
    @Singleton
    fun provideValidateTransferRequestUseCase(
        validateNationalId: ValidateNationalIdUseCase,
        validatePrice: ValidatePriceUseCase,
        validateImages: ValidateTransferImagesUseCase
    ): ValidateTransferRequestUseCase {
        return ValidateTransferRequestUseCase(validateNationalId, validatePrice, validateImages)
    }

    @Provides
    @Singleton
    fun provideCreateTransferRequestUseCase(repository: TransferRequestRepository): CreateTransferRequestUseCase {
        return CreateTransferRequestUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetBuyerRequestsUseCase(repository: TransferRequestRepository): GetBuyerRequestsUseCase {
        return GetBuyerRequestsUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideApproveTransferRequestUseCase(repository: TransferRequestRepository): ApproveTransferRequestUseCase {
        return ApproveTransferRequestUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideRejectTransferRequestUseCase(repository: TransferRequestRepository): RejectTransferRequestUseCase {
        return RejectTransferRequestUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetUserDataUseCase(repository: com.abdallahyasser.maslahty.domain.home.repository.HomeRepoInterface): com.abdallahyasser.maslahty.domain.home.useCase.GetUserDataUseCase {
        return com.abdallahyasser.maslahty.domain.home.useCase.GetUserDataUseCase(repository)
    }
}
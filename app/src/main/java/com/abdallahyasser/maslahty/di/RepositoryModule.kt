package com.abdallahyasser.maslahty.di

import com.abdallahyasser.maslahty.data.auth.AuthRepositoryImpl
import com.abdallahyasser.maslahty.data.vehicle.repoImpl.VehicleRepositoryImpl
import com.abdallahyasser.maslahty.data.violations.repoImpl.ViolationRepoImpl
import com.abdallahyasser.maslahty.domain.auth.repo.AuthRepository
import com.abdallahyasser.maslahty.domain.vehicle.repo.VehicleRepository
import com.example.maslahty.domain.repositories.ViolationRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideAuthRepository(impl: AuthRepositoryImpl): AuthRepository = impl

    @Provides
    @Singleton
    fun provideVehicleRepository(impl: VehicleRepositoryImpl): VehicleRepository = impl

    @Provides
    @Singleton
    fun provideViolationRepository(impl: ViolationRepoImpl): ViolationRepository = impl

}
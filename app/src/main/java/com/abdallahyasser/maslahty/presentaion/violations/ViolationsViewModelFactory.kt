package com.example.maslahty.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.abdallahyasser.maslahty.data.repoImpl.ViolationRepositoryImpl
import com.abdallahyasser.maslahty.data.repoImpl.VehicleRepositoryImpl
import com.abdallahyasser.maslahty.domain.violation.usecase.GetUserVehiclesUseCase
import com.example.maslahty.domain.usecases.violation.CheckViolationsForTransferUseCase
import com.example.maslahty.domain.usecases.violation.GetVehicleViolationsUseCase

class ViolationsViewModelFactory : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ViolationsViewModel::class.java)) {
            val violationRepository = ViolationRepositoryImpl()
            val vehicleRepository = VehicleRepositoryImpl()

            return ViolationsViewModel(
                getUserVehiclesUseCase = GetUserVehiclesUseCase(vehicleRepository),
                getVehicleViolationsUseCase = GetVehicleViolationsUseCase(violationRepository),
                checkViolationsForTransferUseCase = CheckViolationsForTransferUseCase(violationRepository)
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}


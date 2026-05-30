package com.example.maslahty.domain.usecases.violation

import com.abdallahyasser.maslahty.domain.common.Result
import com.example.maslahty.domain.entities.Violation
import com.abdallahyasser.maslahty.domain.violation.repo.ViolationRepository

class GetVehicleViolationsUseCase(private val violationRepository: ViolationRepository) {
    suspend operator fun invoke(vehicleId: String): Result<List<Violation>> {
        return violationRepository.getVehicleViolations(vehicleId)
    }
}



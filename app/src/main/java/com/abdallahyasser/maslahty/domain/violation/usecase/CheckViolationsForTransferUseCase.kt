package com.example.maslahty.domain.usecases.violation

import com.abdallahyasser.maslahty.domain.common.Result
import com.abdallahyasser.maslahty.domain.violation.repo.ViolationRepository

class CheckViolationsForTransferUseCase(private val violationRepository: ViolationRepository) {
    suspend operator fun invoke(vehicleId: String): Result<Boolean> {
        return violationRepository.hasUnpaidViolations(vehicleId)
    }
}

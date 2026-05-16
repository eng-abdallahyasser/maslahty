package com.abdallahyasser.maslahty.domain.transfer.usecase

import com.abdallahyasser.maslahty.domain.transfer.entity.ValidationResult
import com.abdallahyasser.maslahty.domain.utils.ValidationUtil

class ValidateNationalIdUseCase {
    operator fun invoke(nationalId: String): ValidationResult {
        if (nationalId.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "الرقم القومي مطلوب"
            )
        }
        if (!ValidationUtil.isValidNationalId(nationalId)) {
            return ValidationResult(
                successful = false,
                errorMessage = "الرقم القومي يجب أن يتكون من 14 رقم"
            )
        }
        return ValidationResult(successful = true)
    }
}

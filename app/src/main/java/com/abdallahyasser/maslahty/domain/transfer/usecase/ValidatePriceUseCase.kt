package com.abdallahyasser.maslahty.domain.transfer.usecase

import com.abdallahyasser.maslahty.domain.transfer.entity.ValidationResult
import com.abdallahyasser.maslahty.domain.utils.ValidationUtil

class ValidatePriceUseCase {
    operator fun invoke(price: Double): ValidationResult {
        if (!ValidationUtil.isValidPrice(price)) {
            return ValidationResult(
                successful = false,
                errorMessage = "السعر يجب أن يكون أكبر من صفر"
            )
        }
        return ValidationResult(successful = true)
    }
}

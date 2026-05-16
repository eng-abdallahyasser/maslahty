package com.abdallahyasser.maslahty.domain.transfer.usecase

import com.abdallahyasser.maslahty.domain.transfer.entity.ValidationResult

class ValidateTransferImagesUseCase {
    operator fun invoke(imageUrls: List<String>): ValidationResult {
        if (imageUrls.isEmpty()) {
            return ValidationResult(
                successful = false,
                errorMessage = "يجب رفع صورة واحدة على الأقل (عقد البيع أو بطاقة المشتري)"
            )
        }
        return ValidationResult(successful = true)
    }
}

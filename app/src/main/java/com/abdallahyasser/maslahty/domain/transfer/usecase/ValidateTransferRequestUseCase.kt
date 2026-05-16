package com.abdallahyasser.maslahty.domain.transfer.usecase

import com.abdallahyasser.maslahty.domain.transfer.entity.TransferRequest
import com.abdallahyasser.maslahty.domain.transfer.entity.ValidationResult

class ValidateTransferRequestUseCase(
    private val validateNationalId: ValidateNationalIdUseCase,
    private val validatePrice: ValidatePriceUseCase,
    private val validateImages: ValidateTransferImagesUseCase
) {
    operator fun invoke(request: TransferRequest): ValidationResult {
        // Validate Price
        val priceResult = validatePrice(request.price)
        if (!priceResult.successful) return priceResult

        // Validate Images
        val imagesResult = validateImages(request.imageUrls)
        if (!imagesResult.successful) return imagesResult

        // Validate Buyer National ID
        val nationalIdResult = validateNationalId(request.buyerNationalId)
        if (!nationalIdResult.successful) return nationalIdResult

        // Validate Buyer Name
        if (request.buyerName.isBlank()) {
            return ValidationResult(successful = false, errorMessage = "اسم المشتري مطلوب")
        }

        return ValidationResult(successful = true)
    }
}

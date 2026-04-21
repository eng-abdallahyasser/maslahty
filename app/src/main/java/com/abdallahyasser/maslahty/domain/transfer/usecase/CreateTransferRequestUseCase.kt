package com.abdallahyasser.maslahty.domain.transfer.usecase

import com.abdallahyasser.maslahty.domain.transfer.entity.TransferRequest
import com.abdallahyasser.maslahty.domain.transfer.repo.TransferRequestRepository

class CreateTransferRequestUseCase(private  val transferRequestRepository: TransferRequestRepository) {
    suspend operator fun invoke(request: TransferRequest): Result<TransferRequest> {
        return transferRequestRepository.createTransferRequest(request)
    }
}
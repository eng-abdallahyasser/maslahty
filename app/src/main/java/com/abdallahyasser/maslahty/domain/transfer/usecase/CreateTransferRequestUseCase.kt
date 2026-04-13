package com.abdallahyasser.maslahty.domain.transfer.usecase

import com.abdallahyasser.maslahty.domain.transfer.repo.TransferRequestRepository
import com.example.maslahty.domain.entities.TransferRequest

class CreateTransferRequestUseCase(private  val transferRequestRepository: TransferRequestRepository) {
    suspend operator fun invoke(request: TransferRequest): Result<TransferRequest> {
        return transferRequestRepository.createTransferRequest(request)
    }
}
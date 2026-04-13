package com.abdallahyasser.maslahty.domain.transfer.usecase


import com.abdallahyasser.maslahty.domain.transfer.repo.TransferRequestRepository
import com.example.maslahty.domain.entities.TransferRequest

class ApproveTransferRequestUseCase(private val transferRequestRepository: TransferRequestRepository) {
    suspend operator fun invoke(requestId: String, buyerId: String): Result<TransferRequest>{
       return transferRequestRepository.approveTransferRequest(requestId, buyerId)
    }


    }


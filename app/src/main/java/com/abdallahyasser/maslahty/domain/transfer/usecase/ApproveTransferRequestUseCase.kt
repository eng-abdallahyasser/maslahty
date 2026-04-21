package com.abdallahyasser.maslahty.domain.transfer.usecase


import com.abdallahyasser.maslahty.domain.transfer.repo.TransferRequestRepository
import com.abdallahyasser.maslahty.domain.transfer.entity.TransferRequest

class ApproveTransferRequestUseCase(private val transferRequestRepository: TransferRequestRepository) {
    suspend operator fun invoke(requestId: String, buyerId: String): Result<TransferRequest>{
       return transferRequestRepository.approveTransferRequest(requestId, buyerId)
    }


    }


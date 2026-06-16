package com.abdallahyasser.maslahty.domain.requests

import com.abdallahyasser.maslahty.domain.common.Result
import com.abdallahyasser.maslahty.domain.transfer.repo.TransferRequestRepository

class RejectTransferRequestUseCase(private val transferRequestRepository: TransferRequestRepository) {
    suspend operator fun invoke(requestId: String, reason: String): Result<Unit> {
        return transferRequestRepository.rejectTransferRequest(requestId, reason)
    }
}

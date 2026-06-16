package com.abdallahyasser.maslahty.domain.requests

import com.abdallahyasser.maslahty.domain.common.Result
import com.abdallahyasser.maslahty.domain.transfer.entity.TransferRequest
import com.abdallahyasser.maslahty.domain.transfer.entity.TransferStatus
import com.abdallahyasser.maslahty.domain.transfer.repo.TransferRequestRepository

class CancelTransferRequestUseCase(private val transferRequestRepository: TransferRequestRepository) {
    suspend operator fun invoke(requestId: String): Result<TransferRequest> {
        return when (val getResult = transferRequestRepository.getTransferRequestById(requestId)) {
            is Result.Success -> {
                val request = getResult.data
                val updatedRequest = request.copy(status = TransferStatus.CANCELLED)
                transferRequestRepository.updateTransferRequest(updatedRequest)
            }
            is Result.Error -> Result.Error(getResult.exception)
            is Result.Loading -> Result.Loading
        }
    }
}

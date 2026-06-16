package com.abdallahyasser.maslahty.domain.requests

import com.abdallahyasser.maslahty.domain.common.Result
import com.abdallahyasser.maslahty.domain.transfer.entity.TransferRequest
import com.abdallahyasser.maslahty.domain.transfer.repo.TransferRequestRepository

class GetSellerRequestsUseCase(private val transferRequestRepository: TransferRequestRepository) {
    suspend operator fun invoke(sellerId: String): Result<List<TransferRequest>> {
        return transferRequestRepository.getSellerRequests(sellerId)
    }
}

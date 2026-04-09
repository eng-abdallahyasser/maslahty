package com.abdallahyasser.maslahty.domain.transfer.usecase

import com.abdallahyasser.maslahty.domain.transfer.entity.TransferRequest
import com.abdallahyasser.maslahty.domain.transfer.repo.TransferRequestRepository

class GetBuyerRequestsUseCase (private val transferRequestRepository: TransferRequestRepository){
    suspend operator fun invoke(buyerId: String): Result<List<TransferRequest>>{
    return transferRequestRepository.getBuyerRequests(buyerId)
}
}



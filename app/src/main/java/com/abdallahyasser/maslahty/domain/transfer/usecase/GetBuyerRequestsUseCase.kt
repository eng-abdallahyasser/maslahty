package com.abdallahyasser.maslahty.domain.transfer.usecase

import com.abdallahyasser.maslahty.domain.transfer.repo.TransferRequestRepository
import com.example.maslahty.domain.entities.TransferRequest

class GetBuyerRequestsUseCase (private val transferRequestRepository: TransferRequestRepository){
    suspend operator fun invoke(buyerId: String): Result<List<TransferRequest>>{
    return transferRequestRepository.getBuyerRequests(buyerId)
}
}



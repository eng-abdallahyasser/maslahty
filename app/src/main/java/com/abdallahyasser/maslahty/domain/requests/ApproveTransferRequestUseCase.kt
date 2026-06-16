package com.abdallahyasser.maslahty.domain.requests

import com.abdallahyasser.maslahty.domain.common.Result
import com.abdallahyasser.maslahty.domain.transfer.repo.TransferRequestRepository
import com.abdallahyasser.maslahty.domain.transfer.entity.TransferRequest
import com.abdallahyasser.maslahty.domain.vehicle.repo.VehicleRepository

class ApproveTransferRequestUseCase(
    private val transferRequestRepository: TransferRequestRepository,
    private val vehicleRepository: VehicleRepository
) {
    suspend operator fun invoke(requestId: String, buyerId: String): Result<TransferRequest> {
        val approveResult = transferRequestRepository.approveTransferRequest(requestId, buyerId)
        if (approveResult is Result.Success) {
            val request = approveResult.data
            val vehicleId = request.vehicleId
            val sellerId = request.sellerId
            val finalBuyerId = request.buyerId ?: buyerId
            
            if (vehicleId.isNotEmpty() && sellerId.isNotEmpty() && finalBuyerId.isNotEmpty()) {
                val transferResult = vehicleRepository.transferVehicleOwnership(vehicleId, sellerId, finalBuyerId)
                if (transferResult is Result.Error) {
                    return Result.Error(Exception("تمت الموافقة على الطلب ولكن فشل نقل ملكية السيارة: ${transferResult.exception.message}", transferResult.exception))
                }
            }
        }
        return approveResult
    }
}


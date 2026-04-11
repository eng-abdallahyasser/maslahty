package com.abdallahyasser.maslahty.domain.transfer.repo

import com.abdallahyasser.maslahty.domain.transfer.entity.TransferRequest

interface TransferRequestRepository {
    suspend fun createTransferRequest(request: TransferRequest): Result<TransferRequest>
    suspend fun getTransferRequestById(id: String): Result<TransferRequest>
    suspend fun getSellerRequests(sellerId: String): Result<List<TransferRequest>>
    suspend fun getBuyerRequests(buyerId: String): Result<List<TransferRequest>>
    suspend fun updateTransferRequest(request: TransferRequest): Result<TransferRequest>
    suspend fun approveTransferRequest(requestId: String, buyerId: String): Result<TransferRequest>
    suspend fun rejectTransferRequest(requestId: String, reason: String): Result<Unit>
}
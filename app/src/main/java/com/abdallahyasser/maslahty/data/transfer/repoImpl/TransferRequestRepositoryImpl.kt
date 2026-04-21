package com.abdallahyasser.maslahty.data.transfer.repoImpl

import com.abdallahyasser.maslahty.domain.transfer.repo.TransferRequestRepository
import com.example.maslahty.domain.entities.TransferRequest
import com.example.maslahty.domain.entities.TransferStatus
import java.util.Date

class TransferRequestRepositoryImpl : TransferRequestRepository {
    override suspend fun createTransferRequest(request: TransferRequest): Result<TransferRequest> {
        // بنرجع نفس الـ request اللي مبعوت بس بنضيف له ID وتاريخ كأن السيرفر قبله
        return Result.success(
            request.copy(
                id = "REQ_${(1000..9999).random()}",
                createdAt =Date() ,
                updatedAt = Date(),
            )
        )
    }

    override suspend fun getTransferRequestById(id: String): Result<TransferRequest> {
        return Result.success(
            TransferRequest(
                id = id,
                vehicleId = "V-8822",
                sellerId = "S-10",
                buyerId = "B-20",
                price = 320000.0,
                status = TransferStatus.PENDING, // حالة قيد الانتظار
                sellerName = "محمود حسن",
                buyerName = "مصطفى علي",
                createdAt = Date(), // من يوم فات
                updatedAt = Date()
            )
        )
    }

    override suspend fun getSellerRequests(sellerId: String): Result<List<TransferRequest>> {
        // بنرجع قائمة فيها طلبين تجريبيين للبائع
        val mockList = listOf(
            TransferRequest("1", "V-1", sellerId, "B-1",
                100000.0, TransferStatus.PENDING, "أنا (البائع)", "مشتري 1",
                Date(), Date()),
            TransferRequest("2", "V-2", sellerId, "B-2",
                150000.0, TransferStatus.APPROVED_BY_BUYER,
                "أنا (البائع)", "مشتري 2", Date(), Date())
        )
        return Result.success(mockList)
    }

    override suspend fun getBuyerRequests(buyerId: String): Result<List<TransferRequest>> {
        // بنرجع قائمة بطلبات المشتري
        val mockList = listOf(
            TransferRequest("3", "V-3", "S-3", buyerId, 200000.0, TransferStatus.PENDING, "بائع 3", "أنا (المشتري)", Date(), Date())
        )
        return Result.success(mockList)
    }

    override suspend fun updateTransferRequest(request: TransferRequest): Result<TransferRequest> {
        // بنرجع الـ request بعد ما نحدث وقت التعديل
        return Result.success(request.copy(updatedAt = Date()))
    }



    override suspend fun approveTransferRequest(
        requestId: String,
        buyerId: String
    ): Result<TransferRequest> {
        return Result.success(TransferRequest(
            id = requestId,
            vehicleId = "1",
            sellerId = "1",
            buyerId = buyerId,
            price = 100.0,
            status = TransferStatus.APPROVED_BY_BUYER,
            sellerName = "seller",
            buyerName = "buyer",
            createdAt = Date(),
            updatedAt = Date()

        ))
    }

    override suspend fun rejectTransferRequest(
        requestId: String,
        reason: String
    ): Result<Unit> {
        return try {
            // كود الـ API الفعلي هنا مستقبلاً
            Result.success(Unit)
        } catch (e: Exception) {
            // في حالة الخطأ نرجع Failure ومعاه سبب الخطأ
            Result.failure(e)
        }
    }
}
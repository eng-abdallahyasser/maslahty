package com.abdallahyasser.maslahty.presentation.requests

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdallahyasser.maslahty.domain.transfer.entity.TransferRequest
import com.abdallahyasser.maslahty.domain.common.Result
import com.abdallahyasser.maslahty.domain.requests.ApproveTransferRequestUseCase
import com.abdallahyasser.maslahty.domain.transfer.usecase.CreateTransferRequestUseCase
import com.abdallahyasser.maslahty.domain.requests.CancelTransferRequestUseCase
import com.abdallahyasser.maslahty.domain.requests.GetBuyerRequestsUseCase
import com.abdallahyasser.maslahty.domain.requests.GetSellerRequestsUseCase
import com.abdallahyasser.maslahty.domain.requests.RejectTransferRequestUseCase
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RequestsViewModel @Inject constructor(
    private val authService: FirebaseAuth,
    private val getBuyerRequestsUseCase: GetBuyerRequestsUseCase,
    private val getSellerRequestsUseCase: GetSellerRequestsUseCase,
    private val approveTransferRequestUseCase: ApproveTransferRequestUseCase,
    private val rejectTransferRequestUseCase: RejectTransferRequestUseCase,
    private val cancelTransferRequestUseCase: CancelTransferRequestUseCase
) : ViewModel() {

    val currentUserId: String? get() = authService.currentUser?.uid

    private val _buyerRequestsState = MutableStateFlow<TransferState>(TransferState.Initial)
    val buyerRequestsState: StateFlow<TransferState> = _buyerRequestsState

    private val _approveRequestState = MutableStateFlow<TransferState>(TransferState.Initial)
    val approveRequestState: StateFlow<TransferState> = _approveRequestState

    private val _rejectRequestState = MutableStateFlow<TransferState>(TransferState.Initial)
    val rejectRequestState: StateFlow<TransferState> = _rejectRequestState

    private val _cancelRequestState = MutableStateFlow<TransferState>(TransferState.Initial)
    val cancelRequestState: StateFlow<TransferState> = _cancelRequestState

    init {
        getBuyerRequests()
    }

    fun getBuyerRequests(buyerId: String = "") {
        val userId = authService.currentUser?.uid ?: run {
            _buyerRequestsState.value = TransferState.Error("المستخدم غير مسجل دخول")
            return
        }
        viewModelScope.launch {
            _buyerRequestsState.value = TransferState.Loading
            
            val buyerResult = getBuyerRequestsUseCase(userId)
            val sellerResult = getSellerRequestsUseCase(userId)
            
            if (buyerResult is Result.Success && sellerResult is Result.Success) {
                val combined = (buyerResult.data + sellerResult.data)
                    .distinctBy { it.id }
                    .sortedByDescending { it.updatedAt }
                _buyerRequestsState.value = TransferState.RequestsLoaded(combined)
            } else if (buyerResult is Result.Error) {
                _buyerRequestsState.value = TransferState.Error(buyerResult.exception.message ?: "فشل في تحميل طلبات المشتري")
            } else if (sellerResult is Result.Error) {
                _buyerRequestsState.value = TransferState.Error(sellerResult.exception.message ?: "فشل في تحميل طلبات البائع")
            } else {
                _buyerRequestsState.value = TransferState.Loading
            }
        }
    }

    fun approveRequest(requestId: String, buyerId: String) {
        viewModelScope.launch {
            _approveRequestState.value = TransferState.Loading
            val result = approveTransferRequestUseCase(requestId, buyerId)
            _approveRequestState.value = when (result) {
                is Result.Success -> TransferState.RequestApproved(result.data)
                is Result.Error -> TransferState.Error(result.exception.message ?: "Unknown error")
                is Result.Loading -> TransferState.Loading
            }
        }
    }

    fun rejectRequest(requestId: String, reason: String = "تم الرفض من قبل المشتري") {
        viewModelScope.launch {
            _rejectRequestState.value = TransferState.Loading
            val result = rejectTransferRequestUseCase(requestId, reason)
            _rejectRequestState.value = when (result) {
                is Result.Success -> TransferState.RequestRejected(requestId)
                is Result.Error -> TransferState.Error(result.exception.message ?: "Unknown error")
                is Result.Loading -> TransferState.Loading
            }
        }
    }

    fun cancelRequest(requestId: String) {
        viewModelScope.launch {
            _cancelRequestState.value = TransferState.Loading
            val result = cancelTransferRequestUseCase(requestId)
            _cancelRequestState.value = when (result) {
                is Result.Success -> TransferState.RequestCancelled(result.data)
                is Result.Error -> TransferState.Error(result.exception.message ?: "Unknown error")
                is Result.Loading -> TransferState.Loading
            }
        }
    }

    fun resetState() {
        _buyerRequestsState.value = TransferState.Initial
        _approveRequestState.value = TransferState.Initial
        _rejectRequestState.value = TransferState.Initial
        _cancelRequestState.value = TransferState.Initial
    }
}

sealed class TransferState {
    object Initial : TransferState()
    object Loading : TransferState()
    data class RequestsLoaded(val requests: List<TransferRequest>) : TransferState()
    data class RequestApproved(val request: TransferRequest) : TransferState()
    data class RequestRejected(val requestId: String) : TransferState()
    data class RequestCancelled(val request: TransferRequest) : TransferState()
    data class Error(val message: String) : TransferState()
}

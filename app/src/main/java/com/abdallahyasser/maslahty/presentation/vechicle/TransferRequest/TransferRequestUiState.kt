package com.abdallahyasser.maslahty.presentation.vechicle.TransferRequest

import com.abdallahyasser.maslahty.data.local.TransferDraft.TransferDraft

data class TransferRequestUiState(
    val buyerNationalId: String = "",
    val notes: String = "",
    val error: String? = null,
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val vehicleId: String = "",
    val draft: TransferDraft? = null
)

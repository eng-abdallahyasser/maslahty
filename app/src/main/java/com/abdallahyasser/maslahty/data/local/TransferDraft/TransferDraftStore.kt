package com.abdallahyasser.maslahty.data.local.TransferDraft

import androidx.compose.runtime.mutableStateMapOf
import com.abdallahyasser.maslahty.domain.transfer.entity.TransferRequest

object TransferDraftStore {
    val drafts = mutableStateMapOf<String, TransferDraft>()

    var lastLoadedRequests: List<TransferRequest> = emptyList()
}
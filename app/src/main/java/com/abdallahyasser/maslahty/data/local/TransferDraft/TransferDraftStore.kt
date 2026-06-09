package com.abdallahyasser.maslahty.data.local.TransferDraft

import androidx.compose.runtime.mutableStateMapOf
import com.abdallahyasser.maslahty.domain.transfer.entity.TransferRequest

object TransferDraftStore {
    val drafts = mutableStateMapOf<String, TransferDraft>()

    // currently active draft id used when creating a draft before we know the real license plate
    var activeDraftId: String? = null

    var lastLoadedRequests: List<TransferRequest> = emptyList()
}
package com.abdallahyasser.maslahty.data.local.TransferDraft

import androidx.compose.runtime.mutableStateMapOf
import com.example.maslahty.domain.entities.TransferRequest

object TransferDraftStore {
    val drafts = mutableStateMapOf<String, TransferDraft>()

    var lastLoadedRequests: List<TransferRequest> = emptyList()
}
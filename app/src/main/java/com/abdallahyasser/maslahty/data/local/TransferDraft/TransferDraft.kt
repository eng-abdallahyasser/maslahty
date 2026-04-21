package com.abdallahyasser.maslahty.data.local.TransferDraft

import com.abdallahyasser.maslahty.domain.vehicle.entity.Vehicle

data class TransferDraft(
    val vehicle: Vehicle,

    val salePrice: Double? = null,

    val marketPrice: Double? = null,

    val notes: String = "",

    val buyerNationalId: String = ""
)

package com.abdallahyasser.maslahty.presentaion.screens.Requests.RequestsList

data class RequestsListState (
    val requestNumber: Int = 0 ,
    val requestPrice: Int = 0 ,
    val secondParty: String = "",
    val requestStatus: String = "Pending",
)
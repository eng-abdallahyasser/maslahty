package com.abdallahyasser.maslahty.presentaion.home

data class HomeScreenState(
    val fullName: String = "زائر",
    val vehiclesNumber: Int = 0,
    val activeRequests: Int = 0,
    val completedRequests: Int = 0,
)
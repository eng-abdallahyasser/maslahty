package com.abdallahyasser.maslahty.presentaion.screens.home

data class HomeScreenState(
    val fullName: String = "زائر",
    val vehiclesNumber: Int = 0,
    val activeRequests: Int = 0,
    val completedRequests: Int = 0,
)
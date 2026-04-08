package com.abdallahyasser.maslahty.domain.home.entity

data class HomeUserData (
    val fullName: String = "زائر",
    val vehiclesNumber: Int = 0,
    val activeRequests: Int = 0,
    val completedRequests: Int = 0,
    )
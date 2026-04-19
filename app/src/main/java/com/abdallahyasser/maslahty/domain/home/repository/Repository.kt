package com.abdallahyasser.maslahty.domain.home.repository

import com.abdallahyasser.maslahty.domain.home.entity.HomeUserData
import com.abdallahyasser.maslahty.presentaion.home.HomeScreenState

interface Repository {
    fun getUserData(): HomeUserData
}
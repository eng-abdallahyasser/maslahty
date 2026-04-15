package com.abdallahyasser.maslahty.domain.home.repository

import com.abdallahyasser.maslahty.domain.home.entity.HomeUserData

interface Repository {
    fun getUserData(): HomeUserData
}
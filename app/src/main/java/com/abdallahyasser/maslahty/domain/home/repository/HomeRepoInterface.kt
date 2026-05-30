package com.abdallahyasser.maslahty.domain.home.repository

import com.abdallahyasser.maslahty.domain.home.entity.HomeUserData
import com.abdallahyasser.maslahty.domain.common.Result

interface HomeRepoInterface {
    suspend fun getUserData(): Result<HomeUserData>
}
package com.abdallahyasser.maslahty.domain.home.useCase

import com.abdallahyasser.maslahty.domain.home.entity.HomeUserData
import com.abdallahyasser.maslahty.domain.home.repository.HomeRepoInterface
import com.abdallahyasser.maslahty.domain.common.Result

class GetUserDataUseCase(val repo: HomeRepoInterface) {
    suspend operator fun invoke() : Result<HomeUserData> {
        return repo.getUserData()
    }
}
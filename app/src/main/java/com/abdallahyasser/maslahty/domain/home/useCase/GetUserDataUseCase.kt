package com.abdallahyasser.maslahty.domain.home.useCase

import com.abdallahyasser.maslahty.domain.home.entity.HomeUserData
import com.abdallahyasser.maslahty.domain.home.repository.Repository

class GetUserDataUseCase(val repo: Repository) {
    operator fun invoke() : HomeUserData {
        return repo.getUserData()
    }
}
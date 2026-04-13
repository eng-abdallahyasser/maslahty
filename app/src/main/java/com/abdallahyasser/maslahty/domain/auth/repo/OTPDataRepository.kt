package com.abdallahyasser.maslahty.domain.auth.repo

import com.abdallahyasser.maslahty.domain.auth.entity.AuthTokens
import com.abdallahyasser.maslahty.domain.auth.entity.OTPData

interface OTPDataRepository {
    suspend fun sendOTP(nationalId: String, phoneNumber: String): Result<OTPData>
    suspend fun verifyOTP(phoneNumber: String, code: String): Result<AuthTokens>
}
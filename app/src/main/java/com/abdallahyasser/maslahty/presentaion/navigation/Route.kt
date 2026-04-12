package com.abdallahyasser.maslahty.presentaion.navigation

import kotlinx.serialization.Serializable

// بعمل "قائمة ثابتة" بكل الشاشات في التطبيق
sealed class Route {
    @Serializable object Splash
    @Serializable object Login
    @Serializable object Registration
    @Serializable object Home
    @Serializable object VehicleDetails

    // الشاشات اللي بتاخد أرجيومنتس بنحولها لـ Data Class
    @Serializable
    data class OTP(val phoneNumber: String)

    @Serializable
    data class ImageUpload(val vehicleId: String)
}
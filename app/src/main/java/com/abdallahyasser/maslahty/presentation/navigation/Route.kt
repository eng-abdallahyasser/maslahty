package com.abdallahyasser.maslahty.presentation.navigation

import kotlinx.serialization.Serializable

// بعمل "قائمة ثابتة" بكل الشاشات في التطبيق
@Serializable
sealed class Route {
    @Serializable object Splash
    @Serializable object onBoarding
    @Serializable object Login
    @Serializable object Registration
    @Serializable object Home
    @Serializable object VehicleDetails

    @Serializable
     object ViolationsMenuScreen



    // الشاشات اللي بتاخد أرجيومنتس بنحولها لـ Data Class
    @Serializable
    object OTP
    @Serializable
    data class VehicleViolationsScreen(val vehicleId: String)

    @Serializable
    data class ImageUpload(val vehicleId: String)
    @Serializable
    data class Pricing(val vehicleId: String)
    @Serializable
    data class TransferRequestRoute(val vehicleId: String)


}
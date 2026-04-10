package com.abdallahyasser.maslahty.presentaion.navigation
// بعمل "قائمة ثابتة" بكل الشاشات في التطبيق
sealed class Screen(val route: String){
    object SplashScreen: Screen("splash")
    object LoginScreen: Screen("login")
    object OTP: Screen("otp/{phoneNumber}")
    {
        fun createRoute(phoneNumber: String) = "otp/$phoneNumber"
    }
    object RegistrationScreen: Screen("registration")
    object HomeScreen: Screen("home")
    object VehicleDetailsScreen : Screen("vehicle_details")
    object ImageUploadScreen : Screen("image_upload/{vehicleId}") {
        fun createRoute(vehicleId: String) = "image_upload/$vehicleId"
    }

}
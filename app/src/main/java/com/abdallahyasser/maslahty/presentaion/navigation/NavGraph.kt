package com.abdallahyasser.maslahty.presentaion.navigation

import com.abdallahyasser.maslahty.presentaion.screens.vechicle.VehicleDetails.VehicleDetailsScreen
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.abdallahyasser.maslahty.presentaion.screens.auth.LoginScreen
import com.abdallahyasser.maslahty.presentaion.screens.auth.OTPVerificationScreen
import com.abdallahyasser.maslahty.presentaion.screens.auth.RegistrationScreen
import com.abdallahyasser.maslahty.presentaion.screens.splash.SplashScreen
import com.abdallahyasser.maslahty.presentaion.screens.transfer.Pricing.PricingScreen
import com.abdallahyasser.maslahty.presentaion.screens.vechicle.ImageUpload.ImageUploadScreen
import com.example.maslahty.presentation.screens.home.HomeScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Route.Splash // بنمرر الـ Object نفسه
    ) {
        composable<Route.Splash> {
            SplashScreen(navController = navController)
        }

        composable<Route.Login> {
            LoginScreen(navController = navController)
        }

        // هنا بنستخدم toRoute لفك البيانات تلقائياً
        composable<Route.OTP> { backStackEntry ->
            val args = backStackEntry.toRoute<Route.OTP>()
            OTPVerificationScreen(
                navController = navController,
                phoneNumber = args.phoneNumber
            )
        }

        composable<Route.Registration> {
            RegistrationScreen(navController = navController)
        }

        composable<Route.Home> {
            HomeScreen(navController = navController)
        }

        composable<Route.VehicleDetails> {
            VehicleDetailsScreen(
                navController = navController,
            )
        }

        composable<Route.ImageUpload> { backStackEntry ->
            val args = backStackEntry.toRoute<Route.ImageUpload>()
            ImageUploadScreen(
                navController = navController,
                vehicleId = args.vehicleId
            )
        }

        composable <Route.Pricing> {
            backStackEntry ->
            val args = backStackEntry.toRoute<Route.Pricing>()
            PricingScreen(
                navController = navController,
                vehicleId = args.vehicleId
            )

        }
    }
}
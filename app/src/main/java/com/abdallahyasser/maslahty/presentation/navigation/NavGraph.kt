package com.abdallahyasser.maslahty.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.abdallahyasser.maslahty.presentation.splash.SplashScreen
import com.abdallahyasser.maslahty.presentation.transfer.imageUpload.ImageUploadScreen
import com.abdallahyasser.maslahty.presentation.transfer.vehicleDetails.VehicleDetailsScreen
import com.abdallahyasser.maslahty.presentation.auth.LoginScreen
import com.abdallahyasser.maslahty.presentation.auth.OTPVerification
import com.abdallahyasser.maslahty.presentation.auth.SignUpScreen
import com.abdallahyasser.maslahty.presentation.onboarding.OnboardingScreen
import com.example.maslahty.presentation.screens.home.HomeScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Route.Splash // بنمرر الـ Object نفسه
    ) {
        composable<Route.Splash> {
            SplashScreen(
                navController = navController
            )
        }
        composable<Route.onBoarding> {
            OnboardingScreen(
                navController = navController
            )
        }

        composable<Route.Login> {
            LoginScreen(
                navController = navController
            )
        }

        // هنا بنستخدم toRoute لفك البيانات تلقائياً
        composable<Route.OTP> { backStackEntry ->
            val args = backStackEntry.toRoute<Route.OTP>()
            OTPVerification(
                navController = navController,
                phoneNumber = args.phoneNumber
            )
        }

        composable<Route.Registration> {
            SignUpScreen(
                navController = navController
            )
        }

        composable<Route.Home> {
            HomeScreen(
                navController = navController
            )
        }

        composable<Route.VehicleDetails> {
            VehicleDetailsScreen(
                navController = navController
            )
        }

        composable<Route.ImageUpload> { backStackEntry ->
            val args = backStackEntry.toRoute<Route.ImageUpload>()
            ImageUploadScreen(
                navController = navController,
                vehicleId = args.vehicleId
            )
        }
    }
}
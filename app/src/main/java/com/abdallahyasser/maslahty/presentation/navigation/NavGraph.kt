package com.abdallahyasser.maslahty.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.abdallahyasser.maslahty.presentation.violations.ViolationsMenuScreen
import com.abdallahyasser.maslahty.presentation.violations.VehicleViolationsScreen
import com.abdallahyasser.maslahty.presentation.home.HomeScreen
import com.abdallahyasser.maslahty.presentation.violations.ViolationsViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.abdallahyasser.maslahty.presentation.auth.LoginScreen
import com.abdallahyasser.maslahty.presentation.auth.OTPVerification
import com.abdallahyasser.maslahty.presentation.auth.SignUpScreen
import com.abdallahyasser.maslahty.presentation.onboarding.OnboardingScreen
import com.abdallahyasser.maslahty.presentation.splash.SplashScreen
import com.abdallahyasser.maslahty.presentation.transfer.imageUpload.ImageUploadScreen
import com.abdallahyasser.maslahty.presentation.vechicle.VehicleDetails.VehicleDetailsScreen


@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Route.Home
    ) {
        composable<Route.Splash> {
            SplashScreen(navController = navController)
        }
        composable<Route.onBoarding> {
            OnboardingScreen(navController = navController)
        }
        composable<Route.Login> {
            LoginScreen(navController = navController)
        }
        composable<Route.OTP> {  backStackEntry ->
            OTPVerification(
                navController = navController,
            )
        }
        composable<Route.Registration> {
            SignUpScreen(navController = navController)
        }
        composable<Route.Home> {
            HomeScreen(navController = navController)
        }
        composable<Route.VehicleDetails> {
            VehicleDetailsScreen(navController = navController)
        }
        composable<Route.ViolationsMenuScreen> {
            ViolationsMenuScreen(navController = navController)
        }
        composable<Route.VehicleViolationsScreen> { backStackEntry ->
            val args = backStackEntry.toRoute<Route.VehicleViolationsScreen>()
            VehicleViolationsScreen(
                navController = navController,
                vehicleId = args.vehicleId

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

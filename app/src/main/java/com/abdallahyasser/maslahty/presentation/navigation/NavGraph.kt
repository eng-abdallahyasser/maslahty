package com.abdallahyasser.maslahty.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.abdallahyasser.maslahty.presentaion.screens.splash.SplashScreen
import com.abdallahyasser.maslahty.presentaion.screens.transfer.imageUpload.ImageUploadScreen
import com.abdallahyasser.maslahty.presentaion.screens.transfer.vehicleDetails.VehicleDetailsScreen
import com.abdallahyasser.maslahty.presentaion.screens.violations.ViolationsMenuScreen
import com.abdallahyasser.maslahty.presentaion.screens.violations.VehicleViolationsScreen
import com.abdallahyasser.maslahty.presentaion.view.auth.LoginScreen
import com.abdallahyasser.maslahty.presentaion.view.auth.OTPVerification
import com.abdallahyasser.maslahty.presentaion.view.auth.SignUpScreen
import com.abdallahyasser.maslahty.presentaion.view.onboarding.OnboardingScreen
import com.example.maslahty.presentation.screens.home.HomeScreen
import com.example.maslahty.presentation.viewmodels.ViolationsViewModel
import com.example.maslahty.presentation.viewmodels.ViolationsViewModelFactory
import com.abdallahyasser.maslahty.data.repoImpl.ViolationRepositoryImpl
import com.abdallahyasser.maslahty.data.repoImpl.VehicleRepositoryImpl
import androidx.lifecycle.viewmodel.compose.viewModel

// Create dependencies singleton
object AppDependencies {
    val violationsViewModelFactory = ViolationsViewModelFactory()
}

@Composable
fun NavGraph(navController: NavHostController) {
    val violationsViewModel: ViolationsViewModel = viewModel(factory = AppDependencies.violationsViewModelFactory)
    NavHost(
        navController = navController,
        startDestination = Route.Splash
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
        composable<Route.OTP> { backStackEntry ->
            val args = backStackEntry.toRoute<Route.OTP>()
            OTPVerification(
                navController = navController,
                phoneNumber = args.phoneNumber
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
            ViolationsMenuScreen(navController = navController, viewModel = violationsViewModel)
        }
        composable<Route.VehicleViolationsScreen> { backStackEntry ->
            val args = backStackEntry.toRoute<Route.VehicleViolationsScreen>()
            VehicleViolationsScreen(
                navController = navController,
                vehicleId = args.vehicleId,
                viewModel = violationsViewModel
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

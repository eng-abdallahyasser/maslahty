package com.abdallahyasser.maslahty.presentaion.navigation
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.abdallahyasser.maslahty.presentaion.auth.LoginScreen
import com.abdallahyasser.maslahty.presentaion.auth.OTPVerification
import com.abdallahyasser.maslahty.presentaion.auth.SignUpScreen
import com.abdallahyasser.maslahty.presentaion.onboarding.OnboardingScreen
import com.example.maslahty.presentation.viewmodels.ViolationsViewModel
import com.example.maslahty.presentation.viewmodels.ViolationsViewModelFactory
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
            _root_ide_package_.com.abdallahyasser.maslahty.presentaion.splash.SplashScreen(
                navController = navController
            )
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
            _root_ide_package_.com.example.maslahty.presentation.screens.home.HomeScreen(
                navController = navController
            )
        }
        composable<Route.VehicleDetails> {
            _root_ide_package_.com.abdallahyasser.maslahty.presentaion.transfer.vehicleDetails.VehicleDetailsScreen(
                navController = navController
            )
        }
        composable<Route.ViolationsMenuScreen> {
            _root_ide_package_.com.abdallahyasser.maslahty.presentaion.violations.ViolationsMenuScreen(
                navController = navController,
                viewModel = violationsViewModel
            )
        }
        composable<Route.VehicleViolationsScreen> { backStackEntry ->
            val args = backStackEntry.toRoute<Route.VehicleViolationsScreen>()
            _root_ide_package_.com.abdallahyasser.maslahty.presentaion.violations.VehicleViolationsScreen(
                navController = navController,
                vehicleId = args.vehicleId,
                viewModel = violationsViewModel
            )
        }
        composable<Route.ImageUpload> { backStackEntry ->
            val args = backStackEntry.toRoute<Route.ImageUpload>()
            _root_ide_package_.com.abdallahyasser.maslahty.presentaion.transfer.imageUpload.ImageUploadScreen(
                navController = navController,
                vehicleId = args.vehicleId
            )
        }
    }
}

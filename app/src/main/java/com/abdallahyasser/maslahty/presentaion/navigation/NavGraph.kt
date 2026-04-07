package com.abdallahyasser.maslahty.presentaion.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.abdallahyasser.maslahty.presentaion.screens.auth.LoginScreen
import com.abdallahyasser.maslahty.presentaion.screens.auth.OTPVerificationScreen
import com.abdallahyasser.maslahty.presentaion.screens.auth.RegistrationScreen
import com.abdallahyasser.maslahty.presentaion.screens.home.HomeScreen
import com.abdallahyasser.maslahty.presentaion.screens.transfer.imageUpload.ImageUploadScreen
import com.abdallahyasser.maslahty.presentaion.screens.transfer.vehicleDetails.VehicleDetailsScreen
import com.abdallahyasser.maslahty.presentaion.screens.splash.SplashScreen
import com.abdallahyasser.maslahty.presentaion.screens.transfer.Approval.ApprovalScreen
import com.abdallahyasser.maslahty.presentaion.screens.transfer.Pricing.PricingScreen
import com.abdallahyasser.maslahty.presentaion.screens.transfer.RequestDetails.RequestDetailsScreen
import com.abdallahyasser.maslahty.presentaion.screens.transfer.RequestsManagement.RequestsManagementScreen
import com.abdallahyasser.maslahty.presentaion.screens.transfer.TransferRequest.TransferRequestScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.SplashScreen.route
    )


    {
        composable(Screen.SplashScreen.route) {
                SplashScreen(navController = navController)
        }
        composable(Screen.LoginScreen.route) {
            LoginScreen(navController = navController)
        }
            composable(
                Screen.OTP.route,
                arguments = listOf(navArgument("phoneNumber")
                { type = NavType.StringType }
                )
                ) { backStackEntry ->
                val phoneNumber = backStackEntry.arguments?.getString("phoneNumber").orEmpty()
                OTPVerificationScreen(
                    navController = navController,
                    phoneNumber = phoneNumber
                )
            }
        composable (Screen.RegistrationScreen.route)
        {
          RegistrationScreen(navController=navController )
        }
        composable (Screen.HomeScreen.route){
            HomeScreen(navController=navController)
        }
        composable(Screen.VehicleDetailsScreen.route) {
            VehicleDetailsScreen(navController = navController)
        }

        composable(
            route = Screen.ImageUploadScreen.route,
            arguments = listOf(navArgument("vehicleId") { type = NavType.StringType })
        ) { backStackEntry ->
            val vehicleId = backStackEntry.arguments?.getString("vehicleId").orEmpty()
            ImageUploadScreen(navController = navController, vehicleId = vehicleId)
        }
        composable(
            route = Screen.PricingScreen.route,
            arguments = listOf(navArgument("vehicleId") { type = NavType.StringType })
        ) { backStackEntry ->
            val vehicleId = backStackEntry.arguments?.getString("vehicleId").orEmpty()
            PricingScreen(navController = navController, vehicleId = vehicleId)
        }

        composable(
            route = Screen.TransferRequestScreen.route,
            arguments = listOf(navArgument("vehicleId") { type = NavType.StringType })
        ) { backStackEntry ->
            val vehicleId = backStackEntry.arguments?.getString("vehicleId").orEmpty()
            TransferRequestScreen(navController = navController, vehicleId = vehicleId)
        }

        composable(Screen.RequestsManagementScreen.route) {
            RequestsManagementScreen(navController = navController)
        }

        composable(
            route = Screen.ApprovalScreen.route,
            arguments = listOf(navArgument("requestId") { type = NavType.StringType })
        ) { backStackEntry ->
            val requestId = backStackEntry.arguments?.getString("requestId").orEmpty()
            ApprovalScreen(navController = navController, requestId = requestId)
        }

        composable(
            route = Screen.RequestDetailsScreen.route,
            arguments = listOf(navArgument("requestId") { type = NavType.StringType })
        ) { backStackEntry ->
            val requestId = backStackEntry.arguments?.getString("requestId").orEmpty()
            RequestDetailsScreen(navController = navController, requestId = requestId)
        }

        // More screens will be added here
    }






    }



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
import com.abdallahyasser.maslahty.presentaion.screens.splash.SplashScreen
import com.example.maslahty.presentation.screens.home.HomeScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.SplashScreen.route
    )


    {
        composable(Screen.SplashScreen.route) {
            SplashScreen(navController = navController) {
                navController.navigate("home")
            }
        }
        composable(Screen.LoginScreen.route) {
            LoginScreen(navController = navController)
        }
        composable(
            Screen.OTP.route,
            arguments = listOf(
                navArgument("phoneNumber")
                { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val phoneNumber = backStackEntry.arguments?.getString("phoneNumber").orEmpty()
            OTPVerificationScreen(
                navController = navController,
                phoneNumber = phoneNumber
            )
        }
        composable(Screen.RegistrationScreen.route)
        {
            RegistrationScreen(navController = navController)
        }
        composable(Screen.HomeScreen.route) {
            HomeScreen(navController = navController)
        }
//        composable(Screen.VehicleDetailsScreen.route) {
//            VehicleDetailsScreen(navController = navController)
//        }

//        composable(
//            route = Screen.ImageUploadScreen.route,
//            arguments = listOf(navArgument("vehicleId") { type = NavType.StringType })
//        ) { backStackEntry ->
//            val vehicleId = backStackEntry.arguments?.getString("vehicleId").orEmpty()
//            ImageUploadScreen(navController = navController, vehicleId = vehicleId)
//        }


    }
}



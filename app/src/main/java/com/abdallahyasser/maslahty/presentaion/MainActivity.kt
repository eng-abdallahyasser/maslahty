package com.abdallahyasser.maslahty.presentaion


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.abdallahyasser.maslahty.presentaion.view.HomeScreen
import com.abdallahyasser.maslahty.presentaion.view.SplashScreen.SplashScreen
import com.abdallahyasser.maslahty.presentaion.view.auth.LoginScreen
import com.abdallahyasser.maslahty.presentaion.view.auth.SignUpScreen
import com.abdallahyasser.maslahty.presentaion.view.onboarding.OnboardingScreen
import com.abdallahyasser.maslahty.theme.DarkNavy
import com.abdallahyasser.maslahty.theme.MaslahtyTheme



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaslahtyTheme(dynamicColor = false) {
                var currentScreen by rememberSaveable { mutableStateOf(AppScreen.Splash) }

                when (currentScreen) {
                    AppScreen.Splash -> {
                        Surface(
                            modifier = Modifier.Companion.fillMaxSize(),
                            color = DarkNavy
                        ) {
                            SplashScreen(
                                onSplashFinished = {
                                    currentScreen = AppScreen.Onboarding
                                }
                            )
                        }
                    }

                    AppScreen.Onboarding -> {
                        OnboardingScreen(
                            onFinished = {
                                currentScreen = AppScreen.auth
                            },
                            onSkip = {
                                currentScreen = AppScreen.auth
                            }
                        )
                    }

                    AppScreen.auth -> {
                        NavigationHost()
                    }
                }
            }
        }
    }
}


@Composable
fun NavigationHost() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen( navController = navController)
        }

        composable("signup") {
            SignUpScreen(navController=navController)
        }
        composable("Home") {
            HomeScreen()
        }
    }
    }

enum class AppScreen {
    Splash,
    Onboarding,
    auth
}
package com.abdallahyasser.maslahty.presentaion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.abdallahyasser.maslahty.presentaion.auth.LoginScreen
import com.abdallahyasser.maslahty.presentaion.onboarding.OnboardingScreen
import com.abdallahyasser.maslahty.presentaion.view.HomeScreen
import com.abdallahyasser.maslahty.presentaion.view.SplashScreen.SplashScreen
import com.abdallahyasser.maslahty.theme.DarkNavy
import com.abdallahyasser.maslahty.theme.MaslahtyTheme

private enum class AppScreen {
    Splash, Onboarding, Home
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaslahtyTheme(dynamicColor = false) {


                LoginScreen()


/*                var currentScreen by rememberSaveable { mutableStateOf(AppScreen.Splash) }

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
                                currentScreen = AppScreen.Home
                            },
                            onSkip = {
                                currentScreen = AppScreen.Home
                            }
                        )
                    }

                    AppScreen.Home -> {
                        LoginScreen()
                    }
                }*/
            }
        }
    }
}
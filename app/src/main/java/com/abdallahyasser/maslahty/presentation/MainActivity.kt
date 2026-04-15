package com.abdallahyasser.maslahty.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.abdallahyasser.maslahty.presentation.navigation.NavGraph

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            // 1️⃣ إنشاء NavController
            val navController = rememberNavController()

            // 2️⃣ استدعاء NavGraph
            NavGraph(navController = navController)
//            MaslahtyTheme(dynamicColor = false) {
//                var showSplash by rememberSaveable { mutableStateOf(true) }
//
//                if (showSplash) {
//                    Surface(
//                        modifier = Modifier.Companion.fillMaxSize(),
//                        color = DarkNavy
//                    ) {
//                        SplashScreen(
//                            navController=navController,
//                            onSplashFinished = {
//                                showSplash = false
//                            },
//                        )
//                    }
//                } else {
//                    HomeScreen(navController=navController)
//                }
//            }
        }
    }
}
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
import androidx.navigation.compose.rememberNavController
import com.abdallahyasser.maslahty.presentaion.navigation.NavGraph
import com.abdallahyasser.maslahty.presentaion.screens.splash.SplashScreen
import com.abdallahyasser.maslahty.theme.DarkNavy
import com.abdallahyasser.maslahty.theme.MaslahtyTheme
import com.example.maslahty.presentation.screens.home.HomeScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            // 1️⃣ إنشاء NavController
            val navController = rememberNavController()

            // 2️⃣ استدعاء NavGraph
            NavGraph(navController = navController)

        }
    }
}
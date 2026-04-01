package com.abdallahyasser.maslahty.presentaion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.abdallahyasser.maslahty.presentaion.view.HomeScreen
import com.abdallahyasser.maslahty.presentaion.view.SplashScreen
import com.abdallahyasser.maslahty.theme.DarkNavy
import com.abdallahyasser.maslahty.theme.MaslahtyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaslahtyTheme(dynamicColor = false) {
                var showSplash by rememberSaveable { mutableStateOf(true) }

                if (showSplash) {
                    Surface(
                        modifier = Modifier.Companion.fillMaxSize(),
                        color = DarkNavy
                    ) {
                        SplashScreen(
                            onSplashFinished = {
                                showSplash = false
                            }
                        )
                    }
                } else {
                    HomeScreen()
                }
            }
        }
    }
}
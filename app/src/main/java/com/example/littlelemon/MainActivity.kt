package com.example.littlelemon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.littlelemon.navigation.LittleLemonNavigation
import com.example.littlelemon.presentation.home.HomeScreen
import com.example.littlelemon.presentation.splash.SplashScreen
import com.example.littlelemon.ui.theme.LittleLemonTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LittleLemonTheme {
                LittleLemonNavigation()
            }
        }
    }
}

package com.example.littlelemon.util

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.littlelemon.presentation.cart.CartViewModel

@SuppressLint("UnrememberedGetBackStackEntry")
@Composable
fun rememberRootCartViewModel(navController: NavController): CartViewModel {
    val parentEntry = remember(navController) { navController.getBackStackEntry("root") }
    return hiltViewModel(parentEntry)
}

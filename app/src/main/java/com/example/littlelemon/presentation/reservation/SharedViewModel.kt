package com.example.littlelemon.presentation.reservation

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.littlelemon.navigation.Destinations

@SuppressLint("UnrememberedGetBackStackEntry")
@Composable
fun reservationViewModel(
    modifier: Modifier = Modifier,
    navController: NavController
): ReservationViewModel {

    val currentBackStackEntry by navController.currentBackStackEntryAsState()
//    val parentEntry = remember(currentBackStackEntry) {
//        navController.getBackStackEntry(Destinations.GRAPH.RESERVATION)
//    }
    val parentEntry = remember(navController) {
        navController.getBackStackEntry(Destinations.GRAPH.RESERVATION)
    }
    return hiltViewModel(parentEntry)
}
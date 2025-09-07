package com.example.littlelemon.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.littlelemon.presentation.cart.CartScreen
import com.example.littlelemon.presentation.dish_detail.DishDetail
import com.example.littlelemon.presentation.home.HomeScreen
import com.example.littlelemon.presentation.login.LoginScreen
import com.example.littlelemon.presentation.order.OrderScreen
import com.example.littlelemon.presentation.reservation.reserve_table.ReserveTableScreen
import com.example.littlelemon.presentation.reservation.reserve_table_checkout.ReserveTableCheckoutScreen
import com.example.littlelemon.presentation.reservation.table_reserved.TableReservedScreen
import com.example.littlelemon.presentation.splash.SplashScreen

@Composable
fun LittleLemonNavigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Destinations.SPLASH.route,
        route = Destinations.GRAPH.ROOT
    ) {
        composable(Destinations.SPLASH.route) {
            SplashScreen(navController = navController)
        }
        composable(Destinations.LOGIN.route) {
            LoginScreen(navController = navController)
        }
        composable(Destinations.HOME.route) {
            HomeScreen(navController = navController)
        }
        composable(
            Destinations.DISH_DETAIL.fullRoute,
            arguments = listOf(
                navArgument(Destinations.DISH_DETAIL.ARG_ID)
                {
                    type = NavType.IntType
                })
        ) { backStackEntry ->
            val id =
                requireNotNull(
                    backStackEntry.arguments?.getInt(
                        Destinations.DISH_DETAIL.ARG_ID
                    )
                ) { " Dish id is null" }
            DishDetail(id = id, navController = navController)
        }
        navigation(
            startDestination = Destinations.RESERVE_TABLE.route, route = Destinations.GRAPH.RESERVATION
        ) {
            composable(Destinations.RESERVE_TABLE.route) {
                ReserveTableScreen(navController = navController)
            }
            composable(Destinations.RESERVE_TABLE_CHECKOUT.route) {
                ReserveTableCheckoutScreen(navController = navController)
            }
            composable(Destinations.TABLE_RESERVED.route) {
                TableReservedScreen(navController = navController)
            }
        }
        composable(Destinations.CART.route) {
            CartScreen(navController = navController)
        }
        composable(Destinations.ORDER.route) {
            OrderScreen(navController = navController)
        }
    }
}
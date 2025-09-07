package com.example.littlelemon.navigation

sealed class Destinations(val route: String) {
    object SPLASH : Destinations("splash")

    object LOGIN : Destinations("login")

    object FORGOT : Destinations("forgot")
    object HOME : Destinations("home")
    object DISH_DETAIL : Destinations("dish_detail") {
        const val ARG_ID = "dishId"
        val fullRoute = "$route/{$ARG_ID}"          // for NavHost
        fun getDishId(id: Int) = "$route/$id"     // for navigate()
    }

    object RESERVE_TABLE : Destinations("reserve_table")

    object RESERVE_TABLE_CHECKOUT : Destinations("reserve_table_checkout")

    object TABLE_RESERVED : Destinations("table_reserved")
    object CART : Destinations("cart")

    object GRAPH{
        const val ROOT = "root"
        const val RESERVATION = "reservation"
    }
    object ORDER : Destinations("order")
}
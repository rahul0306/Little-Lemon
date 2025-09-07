package com.example.littlelemon.data.dish

import androidx.annotation.DrawableRes

data class Dish(
    val id: Int,
    val title: String,
    val description: String,
    val price: String,
    @DrawableRes val image: Int,
    val category: String
)
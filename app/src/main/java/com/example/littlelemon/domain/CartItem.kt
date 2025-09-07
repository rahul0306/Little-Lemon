package com.example.littlelemon.domain

data class CartItem(
    val dishId: Int,
    val title: String,
    val price: Double,
    val quantity: Int
) {
    val lineTotal: Double get() = price * quantity
}
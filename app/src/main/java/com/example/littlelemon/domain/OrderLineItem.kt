package com.example.littlelemon.domain

data class OrderLineItem(
    val dishId: Int,
    val title: String,
    val price: Double,
    val quantity: Int,
    val lineTotal: Double
)
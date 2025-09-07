package com.example.littlelemon.domain

import com.google.firebase.Timestamp

data class OrderSummary(
    val id: String,
    val createdAt: Timestamp?,
    val subtotal: Double,
    val deliveryFee: Double,
    val serviceFee: Double,
    val total: Double,
    val itemCount: Int
)
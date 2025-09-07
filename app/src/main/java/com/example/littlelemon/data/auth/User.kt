package com.example.littlelemon.data.auth

data class User(
    val uid: String = "",
    val Username: String = "",
    val email: String = "",
    val password: String = "",
    val createdAt: Long = System.currentTimeMillis()
)
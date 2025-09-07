package com.example.littlelemon.presentation.auth

data class AuthState(
    val isSignUp: Boolean = false,
    val username: String = "",
    val email: String = "",
    val password: String = "",
    val loading: Boolean = false,
    val message: String? = null
)
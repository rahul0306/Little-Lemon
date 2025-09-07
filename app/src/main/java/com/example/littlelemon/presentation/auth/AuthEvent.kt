package com.example.littlelemon.presentation.auth

sealed interface AuthEvent {
    data class UsernameChange(val v: String) : AuthEvent
    data class EmailChange(val v: String) : AuthEvent
    data class PasswordChange(val v: String) : AuthEvent
    data object ToggleMode : AuthEvent
    data object Submit : AuthEvent
}

sealed interface AuthNavEvent{
    data object GoHome: AuthNavEvent
}
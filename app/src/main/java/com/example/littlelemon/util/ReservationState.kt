package com.example.littlelemon.util

data class ReservationState(
    val date: String = "",
    val time: String = "",
    val guestCount: String = "",
    val occasion: String = "",
    val name: String = "",
    val contact: String = "",
    val email: String = "",
) {
    val guestsInt: Int = guestCount.toIntOrNull() ?: 0
    val infoValid: Boolean
        get() =
            date.isNotBlank() && time.isNotBlank() && guestCount.isNotBlank() && occasion.isNotBlank()

    val contactValid: Boolean
        get() {
            val phone =
                contact.isNotBlank() && contact.all { it.isDigit() } && contact.length in 10..12
            val emailId =
                email.contains("@") && email.contains(".")
            val name = name.isNotBlank()
            return infoValid && name && phone && emailId
        }
}
package com.example.littlelemon.presentation.reservation

import androidx.lifecycle.ViewModel
import com.example.littlelemon.util.ReservationState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ReservationViewModel @Inject constructor() : ViewModel() {
    private val _state = MutableStateFlow(ReservationState())
    val state: StateFlow<ReservationState> = _state

    fun updateDate(v: String) = _state.update { it.copy(date = v) }
    fun updateTime(v: String) = _state.update { it.copy(time = v) }
    fun updateGuests(v: String) {
        if (v.isEmpty() || v.all { it.isDigit() }) _state.update { it.copy(guestCount = v) }
    }

    fun updateOccasion(v: String) = _state.update { it.copy(occasion = v) }
    fun updateName(v: String) = _state.update { it.copy(name = v) }
    fun updateContact(v: String) {
        if (v.isEmpty() || v.all { it.isDigit() }) _state.update { it.copy(contact = v) }
    }

    fun updateEmail(v: String) = _state.update { it.copy(email = v) }
    fun clear() = _state.update { ReservationState() }
}
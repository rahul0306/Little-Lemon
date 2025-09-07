package com.example.littlelemon.presentation.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.littlelemon.domain.CartItem
import com.example.littlelemon.data.cart.CartSyncRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val repository: CartSyncRepository
) : ViewModel() {
    val items: StateFlow<List<CartItem>> = repository.observeCart().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000), emptyList()
    )
    val deliveryFee = 2.00
    val serviceFee = 1.00

    fun addOrUpdate(item: CartItem) = viewModelScope.launch {
        repository.addOrUpdate(item)
    }

    fun updateQuantity(dishId: Int, quantity: Int) = viewModelScope.launch {
        repository.updateQuantity(dishId = dishId, quantity = quantity)
    }

    fun remove(dishId: Int) = viewModelScope.launch { repository.remove(dishId) }

    fun clear() = viewModelScope.launch { repository.clearCart() }

    fun subtotal(): Double = items.value.sumOf { it.price * it.quantity }
    fun total(): Double = subtotal() + deliveryFee + serviceFee

    fun syncMerge() = viewModelScope.launch { repository.syncMerge() }

    fun checkout() = viewModelScope.launch { repository.checkOut(deliveryFee, serviceFee) }
}
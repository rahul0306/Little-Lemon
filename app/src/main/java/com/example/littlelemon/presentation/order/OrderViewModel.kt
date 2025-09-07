package com.example.littlelemon.presentation.order

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.littlelemon.data.orders.OrderRepository
import com.example.littlelemon.domain.OrderLineItem
import com.example.littlelemon.domain.OrderSummary
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val repository: OrderRepository
) : ViewModel() {
    private val _orders = MutableStateFlow<List<OrderSummary>>(emptyList())
    val orders: StateFlow<List<OrderSummary>> = _orders.asStateFlow()

    private val _itemsByOrder = MutableStateFlow<Map<String, List<OrderLineItem>>>(emptyMap())
    val itemsByOrder: StateFlow<Map<String, List<OrderLineItem>>> = _itemsByOrder.asStateFlow()

    init {
        viewModelScope.launch {
            repository.observeOrders().collectLatest { _orders.value = it }
        }
    }

    fun itemsLoaded(orderId: String) {
        if (_itemsByOrder.value.containsKey(orderId)) return
        viewModelScope.launch {
            val items = repository.getOrderItems(orderId)
            _itemsByOrder.value = _itemsByOrder.value + (orderId to items)
        }
    }
}
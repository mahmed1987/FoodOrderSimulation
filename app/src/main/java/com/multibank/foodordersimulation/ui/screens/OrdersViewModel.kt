package com.multibank.foodordersimulation.ui.screens

import android.util.Log
import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.multibank.foodordersimulation.data.models.Order
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.Queue
import javax.inject.Inject

@HiltViewModel
class OrdersViewModel @Inject constructor() : ViewModel() {

  //orders queue
  private val ordersQueue: MutableStateFlow<ArrayDeque<Order>> = MutableStateFlow(ArrayDeque())
  private val mutableOrder: MutableStateFlow<Order> = MutableStateFlow(Order.empty())

  init {
    Log.d("OrdersViewModel", "Creating")
  }

  val orderState: StateFlow<OrderUiState> =
    combine(ordersQueue, mutableOrder) { orders, mutableOrder ->
      OrderUiState.Loading
    }.stateIn(
      scope = viewModelScope,
      started = SharingStarted.WhileSubscribed(5_000),
      initialValue = OrderUiState.Loading
    )

  fun addOrderToQueue(order: Order) {
    viewModelScope.launch {
      mutableOrder.emit(order)
    }
  }


}

@Immutable
sealed interface OrderUiState {
  data class Success(val orders: List<Order>) : OrderUiState
  data class OrderStatusChanged(val orderId: Int, val status: Order.Status) : OrderUiState
  object Error : OrderUiState
  object Loading : OrderUiState
}
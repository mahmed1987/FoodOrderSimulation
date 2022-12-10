package com.multibank.foodordersimulation.ui.screens

import android.os.CountDownTimer
import android.util.Log
import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.multibank.foodordersimulation.data.models.Order
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class OrdersViewModel @Inject constructor() : ViewModel() {

  //orders queue
  private val ordersQueue: MutableStateFlow<ArrayDeque<Order>> = MutableStateFlow(ArrayDeque())
  private val event: MutableStateFlow<OrderIntention> =
    MutableStateFlow(OrderIntention.InitializeScreen)

  private val deliveredOrderTimers: MutableList<CountDownTimer> = mutableListOf()


  override fun onCleared() {
    super.onCleared()
    deliveredOrderTimers.forEach { it.cancel() }
  }

  fun advanceOrderStatus(order: Order) {
    executeIntention(OrderIntention.AdvanceStatus(order))
  }

  fun addOrderToQueue() {
    executeIntention(OrderIntention.AddOrderToQueue(Order.create()))
  }

  fun orderById(id: String) = ordersQueue.value.findOrderById(id)

  private fun removeDeliveredOrder(order: Order) {
    executeIntention(OrderIntention.RemovedDeliveredOrder(order))
  }

  val orderState: StateFlow<OrderUiState> =
    combine(ordersQueue, event) { orders, intention ->
      OrderUiState.Success(reduce(orders, intention))
    }.stateIn(
      scope = viewModelScope,
      started = SharingStarted.WhileSubscribed(5_000),
      initialValue = OrderUiState.Loading
    )

  /**
   * Takes in the current orders , performs the intention on it , then returns a new list
   */
  private fun reduce(currentOrders: MutableList<Order>, event: OrderIntention): List<Order> {
    when (event) {
      is OrderIntention.AddOrderToQueue -> currentOrders.add(event.order)
      is OrderIntention.AdvanceStatus -> {
        val order = currentOrders.findOrderById(event.order.id)
        val newStatus = advanceStatus(order.second.status)
        currentOrders[order.first] = order.second.copy(status = newStatus)
        if (newStatus == Order.Status.Delivered) {
          startTimerForOrder(currentOrders[order.first])
        }
      }
      OrderIntention.InitializeScreen -> emptyList<Order>()
      is OrderIntention.RemovedDeliveredOrder -> {
        val order = currentOrders.findOrderById(event.order.id)
        currentOrders.removeAt(order.first)
      }
    }
    return ArrayList(currentOrders)
  }

  private fun startTimerForOrder(order: Order) {
    object : CountDownTimer(15000, 15000) {

      override fun onTick(millisUntilFinished: Long) {
        Log.d("OrderViewModel", "${order.id} marked for delivery")
      }

      override fun onFinish() {
        Log.d("OrderViewModel", "${order.id} delivered")
        removeDeliveredOrder(order)
      }
    }.start()
  }


  private fun List<Order>.findOrderById(id: String): Pair<Int, Order> {
    val order = first { it.id == id }
    val indexOfOrder = indexOf(order)
    return indexOfOrder to order
  }

  private fun List<Order>.findDeliveredOrders() = filter { it.status == Order.Status.Delivered }

  private fun executeIntention(intention: OrderIntention) {
    event.value = intention
  }

  private fun advanceStatus(status: Order.Status) = when (status) {
    Order.Status.New -> Order.Status.Preparing
    Order.Status.Preparing -> Order.Status.Ready
    Order.Status.Ready -> Order.Status.Delivered
    Order.Status.Delivered -> Order.Status.Delivered
  }


}

sealed interface OrderIntention {
  object InitializeScreen : OrderIntention
  data class AddOrderToQueue(val order: Order) : OrderIntention
  data class AdvanceStatus(val order: Order) : OrderIntention
  data class RemovedDeliveredOrder(val order: Order) : OrderIntention
}

@Immutable
sealed interface OrderUiState {
  data class Success(val orders: List<Order>) : OrderUiState
  data class OrderStatusChanged(val orderId: Int, val status: Order.Status) : OrderUiState
  object Error : OrderUiState
  object Loading : OrderUiState
}
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

  private val timer: CountDownTimer = object : CountDownTimer(15000, 15000) {

    override fun onTick(millisUntilFinished: Long) {
    }

    override fun onFinish() {
      removeDeliveredOrders()
      start()
    }
  }

  init {
    timer.start()
  }

  override fun onCleared() {
    super.onCleared()
    timer.cancel()
  }

  private fun initializeCountDownTimer() {
  }

  fun advanceOrderStatus(order: Order) {
    executeIntention(OrderIntention.AdvanceStatus(order))
  }

  fun addOrderToQueue() {
    executeIntention(OrderIntention.AddOrderToQueue(Order.create()))
  }

  private fun removeDeliveredOrders() {
    executeIntention(OrderIntention.RemovedDeliveredOrders)
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
        currentOrders[order.first] = order.second.copy(status = advanceStatus(order.second.status))
      }
      OrderIntention.InitializeScreen -> emptyList<Order>()
      is OrderIntention.RemovedDeliveredOrders -> {
        val orders = currentOrders.findDeliveredOrders()
        currentOrders.removeAll(orders)
      }
    }
    return ArrayList(currentOrders)
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
  object RemovedDeliveredOrders : OrderIntention
}

@Immutable
sealed interface OrderUiState {
  data class Success(val orders: List<Order>) : OrderUiState
  data class OrderStatusChanged(val orderId: Int, val status: Order.Status) : OrderUiState
  object Error : OrderUiState
  object Loading : OrderUiState
}
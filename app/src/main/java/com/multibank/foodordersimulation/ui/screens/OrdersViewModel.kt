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

  /* The backing queue of orders that drives the UI for orders - In reality this should be persisted in Room*/
  private val ordersQueue: MutableStateFlow<ArrayDeque<Order>> = MutableStateFlow(ArrayDeque())

  /* The intention that would mutate the state of the UI*/
  private val event: MutableStateFlow<OrderIntention> =
    MutableStateFlow(OrderIntention.InitializeScreen)

  /* A property that would host all the timers that are present in the system , so that they can be cleared in onCleared*/
  private val deliveredOrderTimers: MutableList<CountDownTimer> = mutableListOf()

  /* State of the UI*/
  val orderState: StateFlow<OrderUiState> =
    combine(ordersQueue, event) { orders, intention ->
      OrderUiState.Success(reduce(orders, intention))
    }.stateIn(
      scope = viewModelScope,
      started = SharingStarted.Eagerly,
      initialValue = OrderUiState.Loading
    )


  //region Public interfaces to the ViewModel
  fun advanceOrderStatus(order: Order) {
    executeIntention(OrderIntention.AdvanceStatus(order))
  }

  fun addOrderToQueue() {
    executeIntention(OrderIntention.AddOrderToQueue(Order.create()))
  }

  fun orderById(id: String) = ordersQueue.value.findOrderById(id)
  //endregion


  /**
   * Takes in the current orders , performs the intention on it , then returns a new list
   */
  private fun reduce(currentOrders: MutableList<Order>, event: OrderIntention): List<Order> {
    when (event) {
      is OrderIntention.AddOrderToQueue -> currentOrders.add(event.order)
      is OrderIntention.AdvanceStatus -> {
        val order = currentOrders.findOrderById(event.order.id)
        order?.let {
          val newStatus = advanceStatus(order.second.status)
          currentOrders[order.first] = order.second.copy(status = newStatus)
          if (newStatus == Order.Status.Delivered) {
            startTimerForOrder(currentOrders[order.first])
          }
        }
      }
      OrderIntention.InitializeScreen -> emptyList<Order>()
      is OrderIntention.RemovedDeliveredOrder -> {
        val order = currentOrders.findOrderById(event.order.id)
        order?.let {
          currentOrders.removeAt(order.first)
        }
      }
    }
    return ArrayList(currentOrders)
  }

  /**
   * When an order moves into the Delivered State , this function below starts the 15 seconds timer for it
   * */
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


  private fun List<Order>.findOrderById(id: String): Pair<Int, Order>? {
    val order = firstOrNull { it.id == id }
    return order?.let {
      val indexOfOrder = indexOf(order)
      indexOfOrder to order
    } ?: kotlin.run { null }
  }

  /**
   * Mutates the State of the UI , by executing this intention against the state
   * */
  private fun executeIntention(intention: OrderIntention) {
    event.value = intention
  }

  /**
   * Advances the stage of the Order , for example from New to Preparation
   * */
  private fun advanceStatus(status: Order.Status) = when (status) {
    Order.Status.New -> Order.Status.Preparing
    Order.Status.Preparing -> Order.Status.Ready
    Order.Status.Ready -> Order.Status.Delivered
    Order.Status.Delivered -> Order.Status.Delivered
  }

  /**
   * After 15 seconds have elapsed , this function below mutate the State via Intention
   * */
  private fun removeDeliveredOrder(order: Order) {
    executeIntention(OrderIntention.RemovedDeliveredOrder(order))
  }

  override fun onCleared() {
    super.onCleared()
    deliveredOrderTimers.forEach { it.cancel() }
  }
}

/**The intentions that would mutate the state of the screen */
sealed interface OrderIntention {
  object InitializeScreen : OrderIntention
  data class AddOrderToQueue(val order: Order) : OrderIntention
  data class AdvanceStatus(val order: Order) : OrderIntention
  data class RemovedDeliveredOrder(val order: Order) : OrderIntention
}

/**The model for the screen*/
@Immutable
sealed interface OrderUiState {
  data class Success(val orders: List<Order>) : OrderUiState
  data class OrderStatusChanged(val orderId: Int, val status: Order.Status) : OrderUiState
  object Error : OrderUiState
  object Loading : OrderUiState
}
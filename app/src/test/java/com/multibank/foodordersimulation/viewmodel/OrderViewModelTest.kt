package com.multibank.foodordersimulation.viewmodel

import com.multibank.foodordersimulation.ui.screens.OrderUiState
import com.multibank.foodordersimulation.ui.screens.OrdersViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class OrderViewModelTest {

  private lateinit var viewModel: OrdersViewModel
  @OptIn(ExperimentalCoroutinesApi::class)
  @get:Rule
  val coroutineRule = MainCoroutineRule()

  @OptIn(ExperimentalCoroutinesApi::class)
  @Before
  fun setUp() {
    viewModel = OrdersViewModel()
  }

  @OptIn(ExperimentalCoroutinesApi::class)
  @Test
  fun `addOrderToQueue, should add a new order to the queue`() = runTest {
    val numberOfOrders: Int = returnNumberOfOrders(viewModel.orderState.value)
    viewModel.addOrderToQueue()
    Assert.assertTrue(returnNumberOfOrders(viewModel.orderState.value) > numberOfOrders)
  }

  private fun returnNumberOfOrders(value: OrderUiState): Int {
    return when (value) {
      OrderUiState.Error -> 0
      OrderUiState.Loading -> 0
      is OrderUiState.OrderStatusChanged -> 0
      is OrderUiState.Success -> value.orders.size
    }
  }
}
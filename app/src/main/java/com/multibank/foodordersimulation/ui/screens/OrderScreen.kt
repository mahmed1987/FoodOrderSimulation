package com.multibank.foodordersimulation.ui.screens

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.multibank.foodordersimulation.data.models.Order
import com.multibank.foodordersimulation.ui.styles.*
import com.multibank.foodordersimulation.ui.theme.FoodOrderSimulationTheme
import com.multibank.foodordersimulation.ui.theme.smallPadding

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun OrderScreen(
  paddingValues: PaddingValues,
  viewModel: OrdersViewModel,
  navigateToOrderDetails: (Int) -> Unit
) {
  val orderUiState: OrderUiState by viewModel.orderState.collectAsStateWithLifecycle()
  Window {
    OrderScreenContent(
      paddingValues,
      orderUiState,
      viewModel::addOrderToQueue,
      navigateToOrderDetails
    )
  }
}

@Composable
fun OrderScreenContent(
  paddingValues: PaddingValues,
  orderUiState: OrderUiState,
  addOrder: () -> Unit,
  navigateToOrderDetails: (Int) -> Unit
) {
  Crossfade(targetState = orderUiState) { uiState ->
    when (uiState) {
      OrderUiState.Error -> TODO()
      OrderUiState.Loading -> CircularProgressIndicator()
      is OrderUiState.OrderStatusChanged -> TODO()
      is OrderUiState.Success -> {
        Column(Modifier.padding(paddingValues)) {
          LargeSpacer()
          AppButton(text = "Place Order") {
            addOrder()
          }
          LargeSpacer()
          OrderItems(
            orders = uiState.orders
          )
        }
      }
    }
  }

}

@Composable
fun OrderItems(orders: List<Order>) {
  LazyColumn {
    items(orders) {
      OrderItem(order = it) {
      }
      SmallSpacer()
    }
  }
}

@Composable
fun OrderItem(order: Order, onOrderClicked: () -> Unit) {
  AppCard(type = CardType.Primary, onClick = { /*TODO*/ }, modifier = Modifier.fillMaxWidth()) {
    Column() {
      Row(verticalAlignment = Alignment.CenterVertically) {
        MediumTitle(text = "Order Id: ")
        SmallSpacer()
        SmallTitle(text = order.id, maxLines = 1)
      }
      AppCard(type = CardType.Secondary) {
        LargeBody(text = stringResource(id = order.status.text))
      }
    }
  }
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewOrderItem() {
  FoodOrderSimulationTheme() {
    OrderItem(order = Order.empty()) {

    }
  }
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewFoodOrderingScreen() {
  FoodOrderSimulationTheme {
    OrderScreenContent(smallPadding, OrderUiState.Success(listOf(Order.empty())), {}) {}
  }
}
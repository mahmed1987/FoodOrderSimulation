package com.multibank.foodordersimulation.ui.screens

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import com.multibank.foodordersimulation.R
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
  navigateToOrderDetails: (Order) -> Unit
) {
  val orderUiState: OrderUiState by viewModel.orderState.collectAsStateWithLifecycle()
  val listState = rememberLazyListState()
  Window {
    OrderScreenContent(
      listState = listState,
      paddingValues = paddingValues,
      orderUiState = orderUiState,
      onOrderStatusClicked = viewModel::advanceOrderStatus,
      addOrder = viewModel::addOrderToQueue,
      navigateToOrderDetails = navigateToOrderDetails
    )
  }
}

@Composable
fun OrderScreenContent(
  paddingValues: PaddingValues,
  orderUiState: OrderUiState,
  onOrderStatusClicked: (Order) -> Unit,
  addOrder: () -> Unit,
  navigateToOrderDetails: (Order) -> Unit,
  listState: LazyListState
) {

  Crossfade(targetState = orderUiState) { uiState ->
    when (uiState) {
      OrderUiState.Error -> TODO()
      OrderUiState.Loading -> CircularProgressIndicator()
      is OrderUiState.OrderStatusChanged -> TODO()
      is OrderUiState.Success -> {
        Column(
          Modifier
            .padding(paddingValues)
        ) {
          LargeSpacer()
          AppButton(text = "Place Order") {
            addOrder()
          }
          LargeSpacer()
          OrderItems(
            listState = listState,
            orders = uiState.orders,
            onOrderStatusClicked = onOrderStatusClicked,
            onOrderClicked = navigateToOrderDetails
          )
        }
      }
    }
  }

}

@Composable
fun OrderItems(
  orders: List<Order>,
  onOrderStatusClicked: (Order) -> Unit,
  onOrderClicked: (Order) -> Unit,
  listState: LazyListState
) {
  LazyColumn(state = listState) {
    items(orders) {
      OrderItem(
        order = it,
        onOrderStatusClicked = onOrderStatusClicked,
        onOrderClicked = onOrderClicked
      )
      SmallSpacer()
    }
  }
}

@Composable
fun OrderItem(
  order: Order,
  onOrderStatusClicked: (Order) -> Unit,
  onOrderClicked: (Order) -> Unit
) {
  AppCard(
    type = CardType.Primary,
    modifier = Modifier
      .fillMaxWidth()

  ) {
    Column {
      Row(verticalAlignment = Alignment.CenterVertically) {
        MediumTitle(text = "Order Id: ")
        SmallSpacer()
        SmallTitle(text = order.id, maxLines = 1)
      }
      MediumSpacer()
      Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
      ) {
        AppCard(
          type = CardType.Tertiary,
          modifier = Modifier.clickable(order.status != Order.Status.Delivered) {
            onOrderStatusClicked(order)
          }) {
          SmallTitle(text = stringResource(id = order.status.text))
        }
        AppButton(
          text = stringResource(id = R.string.see_details),
          type = ButtonType.PrimaryContainer
        ) {
          onOrderClicked(order)
        }
      }
    }
  }
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewOrderItem() {
  FoodOrderSimulationTheme() {
    OrderItem(order = Order.empty(), {}) {

    }
  }
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewFoodOrderingScreen() {
  FoodOrderSimulationTheme {
    OrderScreenContent(
      paddingValues = smallPadding,
      orderUiState = OrderUiState.Success(listOf(Order.empty())),
      onOrderStatusClicked = {},
      addOrder = {},
      {},
      listState = rememberLazyListState()
    )
  }
}
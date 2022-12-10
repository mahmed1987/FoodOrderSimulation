package com.multibank.foodordersimulation.ui.screens

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.multibank.foodordersimulation.data.models.Order
import com.multibank.foodordersimulation.ui.styles.*
import com.multibank.foodordersimulation.ui.theme.FoodOrderSimulationTheme
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

@Composable
fun OrderDetailsScreen(paddingValues: PaddingValues, order: Order) {
  Window() {
    Column(
      Modifier
        .padding(paddingValues)
        .fillMaxSize()
    ) {
      OrderDetailScreenContent(order)
    }
  }
}

@Composable
fun OrderDetailScreenContent(order: Order) {
  AppCard(
    type = CardType.Primary, modifier = Modifier
      .fillMaxWidth()
  ) {
    Row {
      FillSpacer()
      AppCard(type = CardType.Tertiary) {
        SmallTitle(text = stringResource(id = order.status.text))
      }
    }
    Row() {
      Icon(Icons.Filled.Numbers, contentDescription = "")
      MediumTitle(text = "Order Id")
    }
    SmallSpacer()
    AppCard(type = CardType.PrimaryContainer) {
      SmallTitle(text = order.id)
    }
    MediumSpacer()
    Row(
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Icon(Icons.Filled.PunchClock, contentDescription = "")
      MediumTitle(text = "Created At")
      SmallSpacer()
      AppCard(type = CardType.PrimaryContainer) {
        SmallTitle(text = order.createdAt.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)))
      }

    }
  }
}


@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewOrderDetailScreenContent() {
  FoodOrderSimulationTheme() {
    OrderDetailScreenContent(order = Order.empty())
  }
}
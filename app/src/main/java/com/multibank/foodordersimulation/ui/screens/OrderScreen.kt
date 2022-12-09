package com.multibank.foodordersimulation.ui.screens

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.multibank.foodordersimulation.ui.styles.AppButton
import com.multibank.foodordersimulation.ui.styles.LargeSpacer
import com.multibank.foodordersimulation.ui.styles.Window
import com.multibank.foodordersimulation.ui.theme.FoodOrderSimulationTheme
import com.multibank.foodordersimulation.ui.theme.smallPadding

@Composable
fun OrderScreen(
  paddingValues: PaddingValues,
  viewModel: OrdersViewModel,
  navigateToOrderDetails: (Int) -> Unit
) {
  Window {
    OrderScreenContent(paddingValues, navigateToOrderDetails)
  }
}

@Composable
fun OrderScreenContent(paddingValues: PaddingValues, navigateToOrderDetails: (Int) -> Unit) {
  Column(Modifier.padding(paddingValues)) {
    LargeSpacer()
    AppButton(text = "Place Order") {
      navigateToOrderDetails(10)
    }
  }
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewFoodOrderingScreen() {
  FoodOrderSimulationTheme {
    OrderScreenContent(smallPadding) {}
  }
}
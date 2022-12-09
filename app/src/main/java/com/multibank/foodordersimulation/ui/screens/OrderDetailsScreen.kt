package com.multibank.foodordersimulation.ui.screens

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.multibank.foodordersimulation.ui.styles.AppButton
import com.multibank.foodordersimulation.ui.styles.Window
import com.multibank.foodordersimulation.ui.theme.FoodOrderSimulationTheme

@Composable
fun OrderDetailsScreen(viewModel: OrdersViewModel) {
  Window() {
    AppButton(text = "Order details") {
      
    }
  }
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewOrderDetailsScreen() {
  FoodOrderSimulationTheme {
  }
}
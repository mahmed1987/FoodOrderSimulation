package com.multibank.foodordersimulation

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.multibank.foodordersimulation.ui.screens.OrderDetailsScreen
import com.multibank.foodordersimulation.ui.screens.OrderScreen
import com.multibank.foodordersimulation.ui.screens.OrdersViewModel
import com.multibank.foodordersimulation.ui.styles.MediumHeading
import com.multibank.foodordersimulation.ui.theme.FoodOrderSimulationTheme
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalMaterial3Api
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
  @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    WindowCompat.setDecorFitsSystemWindows(window, false)
    setContent {
      FoodOrderSimulationTheme {
        val navController = rememberNavController()
        val currentDestination by navController.currentBackStackEntryAsState()

        Scaffold(topBar = {
          TopAppBar(title = {
            MediumHeading(text = currentDestination?.destination?.screenTitle() ?: "")
          })
        }) { padding ->
          NavHost(navController = navController, startDestination = "orders") {
            composable("orders") {
              val viewModel = hiltViewModel<OrdersViewModel>()
              OrderScreen(padding, viewModel) {
                navController.navigate("orderDetails/${it.id}")
              }
            }
            composable(
              "orderDetails/{orderId}",
              arguments = listOf(navArgument("orderId") { type = NavType.StringType })
            ) { backStackEntry ->
              val viewModel =
                navController.findViewModelByRoute<OrdersViewModel>(backStackEntry, "orders")
              val orderId = backStackEntry.arguments?.getString("orderId")
              orderId?.let {
                val order = viewModel.orderById(orderId)
                OrderDetailsScreen(padding, order.second)
              }
            }
            /*...*/
          }
        }
      }
    }
  }
}

@Composable
fun Greeting(name: String) {
  Text(text = "Hello $name!")
}

fun NavDestination.screenTitle() = when (route) {
  "orders" -> "Orders"
  "orderDetails/{orderId}" -> "Order Details"
  else -> {
    ""
  }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
  FoodOrderSimulationTheme {
    Greeting("Android")
  }
}

@Composable
inline fun <reified T : ViewModel> NavController.findViewModelByRoute(
  backStackEntry: NavBackStackEntry,
  route: String,
): T {
  val parentEntry = remember(backStackEntry) {
    this.getBackStackEntry(route)
  }
  return hiltViewModel(parentEntry)
}
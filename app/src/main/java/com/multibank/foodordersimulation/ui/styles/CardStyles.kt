package com.multibank.foodordersimulation.ui.styles

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.multibank.foodordersimulation.ui.theme.*




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppCard(
  modifier: Modifier = Modifier,
  type: CardType = CardType.Primary,
  isElevated: Boolean = false,
  cornerRadius: Dp = mediumUnit,
  contentPadding: PaddingValues = smallPadding,
  content: @Composable () -> Unit,
) {
  Card(
    modifier = modifier,
    colors = when (type) {
      CardType.Primary -> AppCardDefaults.primaryCardColors()
      CardType.PrimaryContainer -> AppCardDefaults.primaryContainerCardColors()
      CardType.Secondary -> AppCardDefaults.secondaryCardColors()
    },
    elevation = AppCardDefaults.cardElevation(if (isElevated) cardDefaultElevation else 0.dp),
    shape = RoundedCornerShape(cornerRadius)
  ) {
    Column(modifier = Modifier.padding(contentPadding)) {
      content()
    }
  }

}

enum class CardType {
  Primary,
  PrimaryContainer,
  Secondary,
}

object AppCardDefaults {

  @Composable
  fun primaryCardColors(
    containerColor: Color = MaterialTheme.colorScheme.primary,
    contentColor: Color = MaterialTheme.colorScheme.onPrimary,
  ) = CardDefaults.cardColors(
    containerColor = containerColor,
    contentColor = contentColor,
  )

  @Composable
  fun primaryContainerCardColors(
    containerColor: Color = MaterialTheme.colorScheme.primaryContainer,
    contentColor: Color = MaterialTheme.colorScheme.onPrimaryContainer,
  ) = CardDefaults.cardColors(
    containerColor = containerColor,
    contentColor = contentColor,
  )


  @Composable
  fun secondaryCardColors(
    containerColor: Color = MaterialTheme.colorScheme.secondary,
    contentColor: Color = MaterialTheme.colorScheme.onSecondary,
  ) = CardDefaults.cardColors(
    containerColor = containerColor,
    contentColor = contentColor,
  )


  @Composable
  fun cardElevation(defaultElevation: Dp = 8.dp) =
    CardDefaults.cardElevation(defaultElevation = defaultElevation)
}





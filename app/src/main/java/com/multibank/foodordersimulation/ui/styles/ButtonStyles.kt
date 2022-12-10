package com.multibank.foodordersimulation.ui.styles

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.multibank.foodordersimulation.ui.theme.FoodOrderSimulationTheme

enum class ButtonType {
  Primary,
  PrimaryContainer,
  Secondary,
  Tertiary,
}

enum class ButtonSize {
  Large,
  Medium,
  Small,
}

@Composable
fun AppButton(
  type: ButtonType = ButtonType.Primary,
  size: ButtonSize = ButtonSize.Medium,
  contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
  text: String,
  onClick: () -> Unit
) {
  when (type) {
    ButtonType.Primary -> PrimaryAppButton(text = text, onClick = onClick)
    ButtonType.Secondary -> SecondaryAppButton(text = text, onClick = onClick)
    ButtonType.Tertiary -> TertiaryAppButton(text = text, onClick = onClick)
    ButtonType.PrimaryContainer -> PrimaryContainerAppButton(text = text, onClick = onClick)
  }
}

@Composable
private fun PrimaryContainerAppButton(
  onClick: () -> Unit = {},
  text: String,
) {
  ElevatedButton(
    onClick = onClick,
    colors = AppButtonDefaults.primaryContainerColors(),
    elevation = AppButtonDefaults.elevatedButtonElevation()
  ) {
    MediumTitle(text = text)
  }
}

@Composable
private fun PrimaryAppButton(
  onClick: () -> Unit = {},
  text: String,
) {
  ElevatedButton(
    onClick = onClick,
    colors = AppButtonDefaults.primaryColors(),
    elevation = AppButtonDefaults.elevatedButtonElevation()
  ) {
    MediumTitle(text = text)
  }
}


@Composable
private fun SecondaryAppButton(
  onClick: () -> Unit = {},
  text: String,
) {
  Button(onClick = onClick, colors = AppButtonDefaults.secondaryColors()) {
    SmallTitle(text = text)
  }
}

@Composable
private fun TertiaryAppButton(
  onClick: () -> Unit = {},
  text: String,
) {
  Button(onClick = onClick, colors = AppButtonDefaults.tertiaryColors()) {
    SmallTitle(text = text)
  }
}

object AppButtonDefaults {

  @Composable
  fun primaryColors(
    containerColor: Color = MaterialTheme.colorScheme.primary,
    contentColor: Color = MaterialTheme.colorScheme.onPrimary,
  ) = ButtonDefaults.buttonColors(
    containerColor = containerColor,
    contentColor = contentColor,
  )

  @Composable
  fun primaryContainerColors(
    containerColor: Color = MaterialTheme.colorScheme.primaryContainer,
    contentColor: Color = MaterialTheme.colorScheme.onPrimaryContainer,
  ) = ButtonDefaults.buttonColors(
    containerColor = containerColor,
    contentColor = contentColor,
  )

  @Composable
  fun elevatedButtonElevation(defaultElevation: Dp = 4.dp) =
    ButtonDefaults.elevatedButtonElevation(defaultElevation)

  @Composable
  fun secondaryColors(
    containerColor: Color = MaterialTheme.colorScheme.secondary,
    contentColor: Color = MaterialTheme.colorScheme.onSecondary,
  ) = ButtonDefaults.buttonColors(
    containerColor = containerColor,
    contentColor = contentColor,
  )


  @Composable
  fun tertiaryColors(
    containerColor: Color = MaterialTheme.colorScheme.tertiary,
    contentColor: Color = MaterialTheme.colorScheme.onTertiary,
  ) = ButtonDefaults.buttonColors(
    containerColor = containerColor,
    contentColor = contentColor,
  )


}

@Preview
@Composable
fun PreviewPrimaryAppButton() {
  FoodOrderSimulationTheme() {
    PrimaryAppButton({}, "Primary")
  }
}

@Preview
@Composable
fun PreviewSecondaryRezoomButton() {
  FoodOrderSimulationTheme() {
    SecondaryAppButton({}, "Secondary button")
  }
}


@Preview
@Composable
fun PreviewTertiaryRezoomButton() {
  FoodOrderSimulationTheme() {
    TertiaryAppButton({}, "Tertiary button")
  }
}

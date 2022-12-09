package com.multibank.foodordersimulation.ui.styles

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.multibank.foodordersimulation.ui.theme.largeUnit
import com.multibank.foodordersimulation.ui.theme.mediumUnit
import com.multibank.foodordersimulation.ui.theme.smallUnit

@Composable
fun MediumSpacer() {
  Spacer(modifier = Modifier.size(mediumUnit))
}

@Composable
fun LargeSpacer() {
  Spacer(modifier = Modifier.size(largeUnit))
}

@Composable
fun SmallSpacer() {
  Spacer(modifier = Modifier.size(smallUnit))
}

@Composable
fun ColumnScope.FillSpacer(
) {
  Spacer(modifier = Modifier.weight(1f))
}
@Composable
fun RowScope.FillSpacer(
) {
  Spacer(modifier = Modifier.weight(1f))
}



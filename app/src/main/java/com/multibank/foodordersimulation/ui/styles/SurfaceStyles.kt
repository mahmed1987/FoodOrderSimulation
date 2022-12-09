package com.multibank.foodordersimulation.ui.styles

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import com.multibank.foodordersimulation.ui.theme.smallUnit

@Composable
fun Window(
  modifier: Modifier = Modifier,
  content: @Composable ColumnScope.() -> Unit
) {
  Surface(
    modifier = modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
  ) {
    Column(
      modifier = Modifier
        .navigationBarsPadding()
        .padding(start = smallUnit, end = smallUnit)

    ) {
      content(this)
    }
  }
}




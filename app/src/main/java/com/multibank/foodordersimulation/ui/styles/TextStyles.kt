package com.multibank.foodordersimulation.ui.styles

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle

@Composable
fun AppText(fontSize: FontSize = FontSize.Medium, fontFamily: FontFamily = FontFamily.Body, text: String, color: Color = Color.Unspecified) {
  Text(style = getFontStyle(fontSize = fontSize, fontFamily = fontFamily), text = text, color = color)
}

enum class FontSize {
  Large,
  Medium,
  Small
}

@Composable
private fun getFontStyle(fontSize: FontSize, fontFamily: FontFamily): TextStyle {
  return when (fontSize) {
    FontSize.Large -> {
      when (fontFamily) {
        FontFamily.Headline -> MaterialTheme.typography.headlineLarge
        FontFamily.Title -> MaterialTheme.typography.titleLarge
        FontFamily.Body -> MaterialTheme.typography.bodyLarge
        FontFamily.Label -> MaterialTheme.typography.labelLarge
      }
    }
    FontSize.Medium -> {
      when (fontFamily) {
        FontFamily.Headline -> MaterialTheme.typography.headlineMedium
        FontFamily.Title -> MaterialTheme.typography.titleMedium
        FontFamily.Body -> MaterialTheme.typography.bodyMedium
        FontFamily.Label -> MaterialTheme.typography.labelMedium
      }
    }
    FontSize.Small -> {
      when (fontFamily) {
        FontFamily.Headline -> MaterialTheme.typography.headlineSmall
        FontFamily.Title -> MaterialTheme.typography.titleSmall
        FontFamily.Body -> MaterialTheme.typography.bodySmall
        FontFamily.Label -> MaterialTheme.typography.labelSmall
      }
    }
  }
}

enum class FontFamily {
  Headline,
  Title,
  Body,
  Label,
}

@Composable
fun LargeHeading(text: String, color: Color = Color.Unspecified) {
  AppText(fontSize = FontSize.Large, fontFamily = FontFamily.Headline, text = text, color = color)
}
@Composable
fun MediumHeading(text: String, color: Color = Color.Unspecified) {
  AppText(fontSize = FontSize.Medium, fontFamily = FontFamily.Headline, text = text, color = color)
}
@Composable
fun SmallHeading(text: String, color: Color = Color.Unspecified) {
  AppText(fontSize = FontSize.Small, fontFamily = FontFamily.Headline, text = text, color = color)
}
@Composable
fun LargeTitle(text: String, color: Color = Color.Unspecified) {
  AppText(fontSize = FontSize.Large, fontFamily = FontFamily.Title, text = text, color = color)
}
@Composable
fun MediumTitle(text: String, color: Color = Color.Unspecified) {
  AppText(fontSize = FontSize.Medium, fontFamily = FontFamily.Title, text = text, color = color)
}
@Composable
fun SmallTitle(text: String, color: Color = Color.Unspecified) {
  AppText(fontSize = FontSize.Small, fontFamily = FontFamily.Title, text = text, color = color)
}

@Composable
fun LargeBody(text: String, color: Color = Color.Unspecified) {
  AppText(fontSize = FontSize.Large, fontFamily = FontFamily.Body, text = text, color = color)
}
@Composable
fun MediumBody(text: String, color: Color = Color.Unspecified) {
  AppText(fontSize = FontSize.Medium, fontFamily = FontFamily.Body, text = text, color = color)
}
@Composable
fun SmallBody(text: String, color: Color = Color.Unspecified) {
  AppText(fontSize = FontSize.Small, fontFamily = FontFamily.Body, text = text, color = color)
}
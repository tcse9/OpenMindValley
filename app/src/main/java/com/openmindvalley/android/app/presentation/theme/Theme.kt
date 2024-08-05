package com.openmindvalley.android.app.presentation.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Primary,
    secondary = Secondary,
    tertiary = Tertiary,
    background = Primary,
    surface = Primary,
)

@Composable
fun OpenMindValleyTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
      colorScheme = DarkColorScheme,
      typography = Typography,
      content = content
    )
}
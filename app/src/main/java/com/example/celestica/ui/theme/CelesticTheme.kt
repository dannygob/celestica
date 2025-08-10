package com.example.celestica.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// 🎨 Paleta clara
private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF3366CC),
    onPrimary = Color.White,
    secondary = Color(0xFF03DAC5),
    background = Color(0xFFF2F2F2),
    onBackground = Color.Black,
    surface = Color.White,
    error = Color(0xFFB00020)
)

// 🌙 Paleta oscura
private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF3366CC),
    onPrimary = Color.Black,
    secondary = Color(0xFF03DAC5),
    background = Color(0xFF121212),
    onBackground = Color.White,
    surface = Color(0xFF1E1E1E),
    error = Color(0xFFCF6679)
)

// 🌈 Tema general
@Composable
fun CelesticTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colors = if (useDarkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        shapes = CelesticShapes,
        content = content
    )
}

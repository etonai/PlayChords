package com.playchords.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColors = darkColorScheme(
    primary         = PrimaryColor,
    secondary       = SecondaryColor,
    tertiary        = AccentColor,
    background      = DarkBackground,
    surface         = SurfaceColor,
    onPrimary       = Color.Black,
    onSecondary     = Color.Black,
    onBackground    = OnBackground,
    onSurface       = OnSurface
)

@Composable
fun PlayChordsTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = DarkColors,
        content = content
    )
}

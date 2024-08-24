package com.supersonic.heartrate.ui.theme

import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext


private val LightColorScheme = lightColorScheme(
    primary = primary,
    onPrimary = onPrimary,
    secondary = secondary,
    primaryContainer = primaryContainer,
    onPrimaryContainer = onPrimaryContainer,
    secondaryContainer = secondaryContainer,
    background = background

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun HeartRateTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = LightColorScheme
    val context = LocalContext.current as ComponentActivity

    context.enableEdgeToEdge(
        statusBarStyle = SystemBarStyle.light(primary.toArgb(), primary.toArgb()),
        navigationBarStyle = SystemBarStyle.light(background.toArgb(), background.toArgb())
    )

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
package com.example.myapplication.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF38A0FF),//*
    secondary = Color(0xFFC5E6FF),//*
    tertiary = Color(0xFF555555),
    background = Color(0xFF333333),
    surface = Color(0xFF3C3C3C),
    onPrimary = Color.White,
    onSecondary = Color(0xFFC4C4C4),
    onTertiary = Color.White,
    onBackground = Color.White, //*
    onSurface = Color.White,//*
    onError = Color.Yellow
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF38A0FF),//*
    secondary = Color(0xFFC5E6FF),//*
    tertiary = Color(0xFFEEEEEE),
    background = Color.White,
    surface = Color(0xFFF9F9F9),
    onPrimary = Color.White,
    onSecondary = Color(0xFFC4C4C4),
    onTertiary = Color(0xFFC4C4C4),
    onBackground = Color.Black, //*
    onSurface = Color(0xFF333333),
    onError = Color.Red
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
package com.example.movieapp.ui.theme

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
    primary = MovieAppColor.Black,
    onPrimary = MovieAppColor.White,
    primaryContainer = MovieAppColor.RedC1,
    background = MovieAppColor.Black,
    surface = MovieAppColor.Black,
    onSurface = MovieAppColor.White,
    surfaceVariant = MovieAppColor.RedC1,
    onSurfaceVariant = MovieAppColor.White
)

private val LightColorScheme = lightColorScheme(
    primary = MovieAppColor.White,
    onPrimary = MovieAppColor.Black,
    primaryContainer = MovieAppColor.DarkGrayD3,
    secondaryContainer = MovieAppColor.LightGrayD3,
    surface = MovieAppColor.White,
    surfaceVariant = MovieAppColor.White
)

@Composable
fun MovieAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
          Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}
package com.adrifernandev.marsroverchallenge.designsystem.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
fun DSTheme(
    isDarkThemeEnabled: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val context = LocalContext.current
    val isBuildVersionGreaterThanS = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
    val dsColorScheme = when {
        dynamicColor && isBuildVersionGreaterThanS -> {
            if (isDarkThemeEnabled) {
                dynamicDarkColorScheme(context)
            } else {
                dynamicLightColorScheme(context)
            }
        }

        isDarkThemeEnabled -> dsDarkColorScheme
        else -> dsLightColorScheme
    }

    MaterialTheme(
        colorScheme = dsColorScheme,
        typography = dsTypography(),
        content = content
    )
}
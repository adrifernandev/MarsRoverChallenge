package com.adrifernandev.marsroverchallenge.designsystem.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalContext

val LocalDSSpacing = staticCompositionLocalOf {
    DSSpacing()
}

val LocalDSElevations = staticCompositionLocalOf {
    DSElevations()
}

val LocalDSBorder = staticCompositionLocalOf {
    DSBorder()
}

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

object DSTheme {

    val spacing: DSSpacing
        @Composable
        get() = LocalDSSpacing.current

    val elevations: DSElevations
        @Composable
        get() = LocalDSElevations.current

    val border: DSBorder
        @Composable
        get() = LocalDSBorder.current
}
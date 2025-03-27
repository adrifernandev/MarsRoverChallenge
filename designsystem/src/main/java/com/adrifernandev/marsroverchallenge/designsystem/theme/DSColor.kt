package com.adrifernandev.marsroverchallenge.designsystem.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

// Primary brand colors
private val seatCodeGreen = Color(0xFFA7FAAC)

// Neutral colors
private val white = Color(0xFFFFFFFF)
private val black = Color(0xFF000000)
private val grey900 = Color(0xFF212121)
private val grey700 = Color(0xFF757575)
private val grey400 = Color(0xFFBDBDBD)
private val grey200 = Color(0xFFE0E0E0)

val dsDarkColorScheme = darkColorScheme(
    primary = seatCodeGreen,
    onPrimary = white,
    primaryContainer = black,
    onPrimaryContainer = white,
    secondary = white,
    onSecondary = black,
    background = black,
    onBackground = grey400,
    surface = black,
    onSurface = grey200,
    onSurfaceVariant = grey700,
    outline = grey400
)

val dsLightColorScheme = lightColorScheme(
    primary = seatCodeGreen,
    onPrimary = white,
    primaryContainer = white,
    onPrimaryContainer = black,
    secondary = white,
    onSecondary = black,
    background = white,
    onBackground = grey900,
    surface = white,
    onSurface = grey900,
    onSurfaceVariant = grey700,
    outline = grey400
)
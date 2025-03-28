package com.adrifernandev.marsroverchallenge.designsystem.components.spacing

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp

enum class SpacerType {
    HORIZONTAL, VERTICAL
}

@Composable
fun DSSpacer(spacing: Dp, type: SpacerType = SpacerType.HORIZONTAL) {
    val modifier = if (type == SpacerType.HORIZONTAL) {
        Modifier.width(spacing)
    } else {
        Modifier.height(spacing)
    }
    Spacer(modifier = modifier)
}
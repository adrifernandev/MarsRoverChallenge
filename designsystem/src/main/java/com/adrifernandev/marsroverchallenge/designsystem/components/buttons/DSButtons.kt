package com.adrifernandev.marsroverchallenge.designsystem.components.buttons

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.adrifernandev.marsroverchallenge.common.presentation.ui.PhonePreviews
import com.adrifernandev.marsroverchallenge.designsystem.theme.DSTheme

@Composable
private fun DSBaseButton(
    modifier: Modifier = Modifier,
    buttonText: String,
    buttonColors: ButtonColors,
    enabled: Boolean = true,
    onClick: () -> Unit,
) {
    ElevatedButton(
        modifier = modifier
            .height(54.dp)
            .fillMaxWidth(),
        onClick = onClick,
        enabled = enabled,
        elevation = ButtonDefaults.elevatedButtonElevation(
            defaultElevation = DSTheme.elevations.level5,
            pressedElevation = DSTheme.elevations.level0
        ),
        colors = buttonColors,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(DSTheme.spacing.medium)
        ) {
            Text(
                text = buttonText,
                style = MaterialTheme.typography.headlineSmall
            )
        }
    }
}

@Composable
fun DSPrimaryButton(
    modifier: Modifier = Modifier,
    buttonText: String,
    enabled: Boolean = true,
    onClick: () -> Unit,
) {
    DSBaseButton(
        modifier = modifier,
        buttonText = buttonText,
        buttonColors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ),
        enabled = enabled,
        onClick = onClick
    )
}

@PhonePreviews
@Composable
private fun DSPrimaryButtonPreview() {
    DSTheme {
        Column(
            modifier = Modifier.padding(DSTheme.spacing.medium),
            verticalArrangement = Arrangement.spacedBy(DSTheme.spacing.medium)
        ) {
            DSPrimaryButton(
                buttonText = "Primary button",
                onClick = { }
            )
        }
    }
}
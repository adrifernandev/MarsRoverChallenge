package com.adrifernandev.marsroverchallenge.designsystem.components.loading

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog

@Composable
fun DSLoadingDialog(
    modifier: Modifier = Modifier,
) {
    Dialog(onDismissRequest = {}) {
        CircularProgressIndicator(
            modifier = modifier,
            color = MaterialTheme.colorScheme.primary,
        )
    }
}
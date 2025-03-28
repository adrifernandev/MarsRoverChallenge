package com.adrifernandev.marsroverchallenge.presentation.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.adrifernandev.marsroverchallenge.presentation.viewmodel.MainViewModel

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = modifier
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            MainScreenContent(
                state = state,
                onRequestInstructionsClicked = {
                    viewModel.onEvent(MainViewModel.UIEvent.OnRequestRoverInstructions)
                }
            )
        }
    }
}

@Composable
private fun MainScreenContent(
    state: MainViewModel.UIState,
    onRequestInstructionsClicked: () -> Unit
) {
    val initialRoverPosition = state.initialRover?.currentPosition
    val initialRoverDirection = state.initialRover?.currentDirection
    val finalRoverPosition = state.finalRover?.currentPosition
    val finalRoverDirection = state.finalRover?.currentDirection

    Column {
        Text("Rover initial position: ${initialRoverPosition?.x}X ${initialRoverPosition?.y} Y ${initialRoverDirection?.name}")
        Text("Rover final position: ${finalRoverPosition?.x}X ${finalRoverPosition?.y} Y ${finalRoverDirection?.name}")
        Text("Rover instructions: ${state.instructions.toString()}")

        Button(
            onClick = onRequestInstructionsClicked
        ) {
            Text("Request Instructions")
        }
    }
}
package com.adrifernandev.marsroverchallenge.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.adrifernandev.marsroverchallenge.common.presentation.ui.utils.ContentDescriptionUtils.DECORATIVE_CONTENT
import com.adrifernandev.marsroverchallenge.designsystem.components.buttons.DSPrimaryButton
import com.adrifernandev.marsroverchallenge.designsystem.theme.DSTheme
import com.adrifernandev.marsroverchallenge.presentation.R
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
        ) {
            MainScreenBackground()
            MainScreenContent(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
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
    modifier: Modifier = Modifier,
    state: MainViewModel.UIState,
    onRequestInstructionsClicked: () -> Unit
) {
    val initialRoverPosition = state.initialRover?.currentPosition
    val initialRoverDirection = state.initialRover?.currentDirection
    val finalRoverPosition = state.finalRover?.currentPosition
    val finalRoverDirection = state.finalRover?.currentDirection

    Box(
        modifier = modifier
    ) {
        Column {
            Text("Rover initial position: ${initialRoverPosition?.x}X ${initialRoverPosition?.y} Y ${initialRoverDirection?.name}")
            Text("Rover final position: ${finalRoverPosition?.x}X ${finalRoverPosition?.y} Y ${finalRoverDirection?.name}")
            Text("Rover instructions: ${state.instructions.toString()}")
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(DSTheme.spacing.xl),
            verticalArrangement = Arrangement.Bottom
        ) {
            DSPrimaryButton(
                modifier = Modifier
                    .fillMaxWidth(),
                buttonText = "Move rover", //TODO: Replace hardcoded with strings
                onClick = onRequestInstructionsClicked
            )
        }
    }
}

@Composable
private fun MainScreenBackground() {
    Image(
        modifier = Modifier.fillMaxSize(),
        painter = painterResource(R.drawable.img_mars_surface),
        contentDescription = DECORATIVE_CONTENT,
        contentScale = ContentScale.Crop
    )
}
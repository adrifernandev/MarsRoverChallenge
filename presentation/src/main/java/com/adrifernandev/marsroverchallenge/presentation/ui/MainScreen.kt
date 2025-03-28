package com.adrifernandev.marsroverchallenge.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.adrifernandev.marsroverchallenge.common.presentation.ui.PhonePreviews
import com.adrifernandev.marsroverchallenge.common.presentation.ui.utils.ContentDescriptionUtils.DECORATIVE_CONTENT
import com.adrifernandev.marsroverchallenge.designsystem.components.buttons.DSPrimaryButton
import com.adrifernandev.marsroverchallenge.designsystem.theme.DSTheme
import com.adrifernandev.marsroverchallenge.domain.models.Direction
import com.adrifernandev.marsroverchallenge.domain.models.Position
import com.adrifernandev.marsroverchallenge.domain.models.Rover
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
        Column(
            modifier = Modifier
                .padding(DSTheme.spacing.xl)
        ) {
            if (initialRoverPosition != null &&
                finalRoverPosition != null &&
                initialRoverDirection != null &&
                finalRoverDirection != null
            ) {
                RoverPositionInfoModule(
                    text = stringResource(R.string.initial_rover_position),
                    roverPosition = initialRoverPosition,
                    roverDirection = initialRoverDirection
                )
                RoverPositionInfoModule(
                    text = stringResource(R.string.final_rover_position),
                    roverPosition = finalRoverPosition,
                    roverDirection = finalRoverDirection
                )
            } else {
                RoverPositionInfoNotAvailableModule()
            }
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
                buttonText = stringResource(R.string.request_rover_instructions),
                onClick = onRequestInstructionsClicked
            )
        }
    }
}

@Composable
private fun RoverPositionInfoModule(
    text: String,
    roverPosition: Position,
    roverDirection: Direction
) {
    val roverPositionText = stringResource(
        id = R.string.rover_position,
        roverPosition.x,
        roverPosition.y,
        roverDirection.name
    )
    Text(
        text = "$text: $roverPositionText",
        style = MaterialTheme.typography.headlineSmall,
        fontWeight = FontWeight.Medium,
        color = MaterialTheme.colorScheme.secondary,
    )
}

@Composable
private fun RoverPositionInfoNotAvailableModule() {
    Text(
        text = stringResource(R.string.rover_position_not_available),
        style = MaterialTheme.typography.headlineSmall,
        fontWeight = FontWeight.Medium,
        color = MaterialTheme.colorScheme.secondary,
    )
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

@PhonePreviews
@Composable
private fun MainScreenPreview() {
    val initialRover = Rover(
        currentPosition = Position(1, 2),
        currentDirection = Direction.N
    )
    val finalRover = Rover(
        currentPosition = Position(1, 3),
        currentDirection = Direction.N
    )
    DSTheme {
        MainScreenBackground()
        MainScreenContent(
            state = MainViewModel.UIState(
                initialRover = initialRover,
                finalRover = finalRover
            ),
            onRequestInstructionsClicked = {}
        )
    }
}
package com.adrifernandev.marsroverchallenge.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.adrifernandev.marsroverchallenge.common.presentation.ui.PhonePreviews
import com.adrifernandev.marsroverchallenge.common.presentation.ui.utils.ContentDescriptionUtils.DECORATIVE_CONTENT
import com.adrifernandev.marsroverchallenge.designsystem.components.buttons.DSPrimaryButton
import com.adrifernandev.marsroverchallenge.designsystem.components.spacing.DSSpacer
import com.adrifernandev.marsroverchallenge.designsystem.components.spacing.SpacerType
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
    val instructions = state.instructions

    Box(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Bottom
        ) {
            Box(
                modifier = Modifier
                    .clip(
                        RoundedCornerShape(
                            DSTheme.border.radius.xl,
                            DSTheme.border.radius.xl,
                            0.dp,
                            0.dp
                        )
                    )
                    .background(MaterialTheme.colorScheme.primaryContainer)
            ) {
                Column(
                    modifier = Modifier
                        .padding(DSTheme.spacing.xl)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        RoverPositionInfoModule(
                            modifier = Modifier.weight(1f),
                            text = stringResource(R.string.initial_rover_position),
                            roverPosition = initialRoverPosition,
                            roverDirection = initialRoverDirection,
                            horizontalAlignment = Alignment.Start
                        )
                        RoverPositionInfoModule(
                            modifier = Modifier.weight(1f),
                            text = stringResource(R.string.final_rover_position),
                            roverPosition = finalRoverPosition,
                            roverDirection = finalRoverDirection,
                            horizontalAlignment = Alignment.End
                        )
                    }
                    DSSpacer(
                        spacing = DSTheme.spacing.large,
                        type = SpacerType.VERTICAL
                    )
                    RoverInstructionsModule(instructions)
                    DSSpacer(
                        spacing = DSTheme.spacing.large,
                        type = SpacerType.VERTICAL
                    )
                    DSPrimaryButton(
                        modifier = Modifier
                            .fillMaxWidth(),
                        buttonText = stringResource(R.string.request_rover_instructions),
                        onClick = onRequestInstructionsClicked
                    )
                }
            }
        }
    }
}

@Composable
private fun RoverPositionInfoModule(
    modifier: Modifier = Modifier,
    text: String,
    roverPosition: Position?,
    roverDirection: Direction?,
    horizontalAlignment: Alignment.Horizontal
) {
    Column(
        modifier = modifier,
        horizontalAlignment = horizontalAlignment
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
        )
        if (roverPosition != null && roverDirection != null) {
            val roverPositionText = stringResource(
                id = R.string.rover_position,
                roverPosition.x,
                roverPosition.y,
                roverDirection.name
            )
            Text(
                text = roverPositionText,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
            )
        } else {
            RoverPositionInfoNotAvailableModule()
        }
    }
}

@Composable
private fun RoverInstructionsModule(
    instructions: String? = null
) {
    Column {
        Text(
            text = stringResource(R.string.rover_instructions),
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
        )
        instructions?.let {
            Text(
                text = instructions,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
            )
        } ?: run {
            RoverInstructionsNotAvailableModule()
        }
    }
}

@Composable
private fun RoverPositionInfoNotAvailableModule() {
    Text(
        text = stringResource(R.string.rover_position_not_available),
        style = MaterialTheme.typography.headlineSmall,
        fontWeight = FontWeight.Medium,
        color = MaterialTheme.colorScheme.onPrimaryContainer,
    )
}

@Composable
private fun RoverInstructionsNotAvailableModule() {
    Text(
        text = stringResource(R.string.rover_instructions_not_available),
        style = MaterialTheme.typography.headlineSmall,
        fontWeight = FontWeight.Medium,
        color = MaterialTheme.colorScheme.onPrimaryContainer,
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
    val instructions = "LMLMLMLLMM"

    DSTheme {
        MainScreenBackground()
        MainScreenContent(
            state = MainViewModel.UIState(
                initialRover = initialRover,
                finalRover = finalRover,
                instructions = instructions,
            ),
            onRequestInstructionsClicked = {}
        )
    }
}
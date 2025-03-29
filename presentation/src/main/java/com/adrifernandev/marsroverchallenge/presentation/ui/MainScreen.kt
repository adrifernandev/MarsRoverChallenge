package com.adrifernandev.marsroverchallenge.presentation.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
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

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = modifier
    ) { _ ->
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            MainScreenContent(
                modifier = Modifier
                    .fillMaxSize(),
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

    Box {
        MainScreenBackground(
            modifier = Modifier
                .fillMaxSize()
        )
        Column(
            modifier = modifier
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
                        val initialRoverPositionText = initialRoverPosition?.let {
                            stringResource(
                                R.string.rover_position,
                                it.x,
                                it.y,
                                initialRoverDirection?.name ?: ""
                            )
                        }
                        RoverInfoModule(
                            modifier = Modifier.weight(1f),
                            title = stringResource(R.string.initial_rover_position),
                            content = initialRoverPositionText,
                            horizontalAlignment = Alignment.Start
                        )
                        val finalRoverPositionText = finalRoverPosition?.let {
                            stringResource(
                                R.string.rover_position,
                                it.x,
                                it.y,
                                finalRoverDirection?.name ?: ""
                            )
                        }
                        RoverInfoModule(
                            modifier = Modifier.weight(1f),
                            title = stringResource(R.string.final_rover_position),
                            content = finalRoverPositionText,
                            horizontalAlignment = Alignment.End
                        )
                    }
                    DSSpacer(
                        spacing = DSTheme.spacing.large,
                        type = SpacerType.VERTICAL
                    )
                    RoverInfoModule(
                        title = stringResource(R.string.rover_instructions),
                        content = instructions,
                    )
                    DSSpacer(
                        spacing = DSTheme.spacing.large,
                        type = SpacerType.VERTICAL
                    )
                    DSPrimaryButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .navigationBarsPadding(),
                        buttonText = stringResource(R.string.request_rover_instructions),
                        onClick = onRequestInstructionsClicked
                    )
                }
            }
        }
    }
}

@Composable
private fun RoverInfoModule(
    modifier: Modifier = Modifier,
    title: String,
    content: String? = null,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start
) {
    Column(
        modifier = modifier,
        horizontalAlignment = horizontalAlignment
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
        )
        content?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
            )
        } ?: run {
            RoverInfoNotAvailableModule()
        }
    }
}

@Composable
private fun RoverInfoNotAvailableModule() {
    Text(
        text = stringResource(R.string.not_available),
        style = MaterialTheme.typography.headlineSmall,
        fontWeight = FontWeight.Medium,
        color = MaterialTheme.colorScheme.onPrimaryContainer,
    )
}

@Composable
private fun MainScreenBackground(
    modifier: Modifier = Modifier
) {
    Image(
        modifier = modifier,
        painter = painterResource(R.drawable.img_mars_surface),
        contentDescription = DECORATIVE_CONTENT,
        contentScale = ContentScale.Crop
    )
}

@PhonePreviews
@Composable
private fun MainScreenPreviewWithInfo() {
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
        MainScreenBackground(
            modifier = Modifier
                .fillMaxSize()
        )
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

@PhonePreviews
@Composable
private fun MainScreenPreviewWithoutInfo() {
    val initialRover = null
    val finalRover = null
    val instructions = null

    DSTheme {
        MainScreenBackground(
            modifier = Modifier
                .fillMaxSize()
        )
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
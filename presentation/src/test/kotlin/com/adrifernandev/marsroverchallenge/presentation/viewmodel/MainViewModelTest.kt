package com.adrifernandev.marsroverchallenge.presentation.viewmodel

import app.cash.turbine.test
import com.adrifernandev.marsroverchallenge.domain.models.Direction
import com.adrifernandev.marsroverchallenge.domain.models.Instructions
import com.adrifernandev.marsroverchallenge.domain.models.Position
import com.adrifernandev.marsroverchallenge.domain.models.Rover
import com.adrifernandev.marsroverchallenge.domain.models.RoverNavigationResult
import com.adrifernandev.marsroverchallenge.domain.usecases.NavigateRoverUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest {

    private lateinit var viewModel: MainViewModel
    private val navigateRoverUseCase: NavigateRoverUseCase = mockk()
    private val testDispatcher = StandardTestDispatcher()


    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = MainViewModel(navigateRoverUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `onRequestRoverInstructions should update uiState with success result`() = runTest {
        // Given
        val initialRover = Rover(Position(1, 2), Direction.N)
        val finalRover = Rover(Position(1, 3), Direction.N)
        val instructions = Instructions.fromString("LMLMLMLMM")
        val result = RoverNavigationResult(initialRover, instructions, finalRover)

        coEvery { navigateRoverUseCase() } returns flowOf(Result.success(result))

        // When
        viewModel.uiState.test {
            assertEquals(MainViewModel.UIState(), awaitItem())

            viewModel.onEvent(MainViewModel.UIEvent.OnRequestRoverInstructions)

            assertEquals(
                MainViewModel.UIState(isLoading = true),
                awaitItem()
            )

            assertEquals(
                MainViewModel.UIState(
                    initialRover = initialRover,
                    instructions = "LMLMLMLMM",
                    finalRover = finalRover,
                    isLoading = false
                ),
                awaitItem()
            )

            ensureAllEventsConsumed()
        }
    }

    @Test
    fun `onRequestRoverInstructions should update uiState with error on failure`() = runTest {
        // Given
        val errorMessage = "error"
        coEvery { navigateRoverUseCase() } returns flowOf(Result.failure(Exception(errorMessage)))

        // When
        viewModel.uiState.test {
            assertEquals(MainViewModel.UIState(), awaitItem())

            viewModel.onEvent(MainViewModel.UIEvent.OnRequestRoverInstructions)

            assertEquals(
                MainViewModel.UIState(isLoading = true),
                awaitItem()
            )

            assertEquals(
                MainViewModel.UIState(
                    isLoading = false,
                    error = errorMessage
                ),
                awaitItem()
            )

            ensureAllEventsConsumed()
        }
    }
}
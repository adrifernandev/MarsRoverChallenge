package com.adrifernandev.marsroverchallenge.data.repository

import app.cash.turbine.test
import com.adrifernandev.marsroverchallenge.data.datasource.remote.RoverRemoteDataSource
import com.adrifernandev.marsroverchallenge.data.dto.PositionDTO
import com.adrifernandev.marsroverchallenge.data.dto.RoverInstructionsDTO
import com.adrifernandev.marsroverchallenge.domain.models.Direction
import com.adrifernandev.marsroverchallenge.domain.models.Instructions
import com.adrifernandev.marsroverchallenge.domain.models.Plateau
import com.adrifernandev.marsroverchallenge.domain.models.Position
import com.adrifernandev.marsroverchallenge.domain.models.Rover
import com.adrifernandev.marsroverchallenge.domain.models.RoverInput
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class RoverRepositoryImplTest {

    private val roverRemoteDataSource: RoverRemoteDataSource = mockk()
    private val repository = RoverRepositoryImpl(roverRemoteDataSource)

    @Test
    fun `getRoverInstructions should emit mapped RoverInput when data source returns success`() =
        runTest {
            // Given
            val dto = RoverInstructionsDTO(
                topRightCorner = PositionDTO(x = 5, y = 5),
                roverPosition = PositionDTO(x = 1, y = 2),
                roverDirection = "N",
                movements = "LMLMLMLMM"
            )
            val expectedRoverInput = RoverInput(
                plateau = Plateau(Position(5, 5)),
                initialRover = Rover(Position(1, 2), Direction.N),
                instructions = Instructions.fromString("LMLMLMLMM")
            )
            every { roverRemoteDataSource.getRoverInput() } returns flowOf(Result.success(dto))

            // When
            repository.getRoverInstructions().test {
                // Then
                val result = awaitItem()
                assertEquals(Result.success(expectedRoverInput), result)
                awaitComplete()
            }
        }

    @Test
    fun `getRoverInstructions should emit failure when data source returns failure`() = runTest {
        // Given
        val exception = RuntimeException("Data source failed")
        every { roverRemoteDataSource.getRoverInput() } returns flowOf(Result.failure(exception))

        // When
        repository.getRoverInstructions().test {
            // Then
            val result = awaitItem()
            assertEquals(Result.failure<RoverInput>(exception), result)
            awaitComplete()
        }
    }
}
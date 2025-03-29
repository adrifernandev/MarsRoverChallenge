package com.adrifernandev.marsroverchallenge.domain.usecases

import com.adrifernandev.marsroverchallenge.domain.models.Direction
import com.adrifernandev.marsroverchallenge.domain.models.Instructions
import com.adrifernandev.marsroverchallenge.domain.models.Plateau
import com.adrifernandev.marsroverchallenge.domain.models.Position
import com.adrifernandev.marsroverchallenge.domain.models.Rover
import com.adrifernandev.marsroverchallenge.domain.models.RoverInput
import com.adrifernandev.marsroverchallenge.domain.models.RoverNavigationResult
import com.adrifernandev.marsroverchallenge.domain.repository.RoverRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class NavigateRoverUseCaseTest {

    private lateinit var useCase: NavigateRoverUseCase
    private val roverRepository: RoverRepository = mockk()

    @Before
    fun setUp() {
        useCase = NavigateRoverUseCase(roverRepository)
    }

    @Test
    fun `invoke should return moved Rover with valid instructions`() = runTest {
        // Given
        val rover = Rover(Position(1, 2), Direction.N)
        val plateau = Plateau(Position(5, 5))
        val instructions = Instructions.fromString("LMLMLMLMM")
        val input = RoverInput(plateau, rover, instructions)
        val expectedResult = RoverNavigationResult(
            initialRover = rover,
            instructions = instructions,
            finalRover = Rover(Position(1, 3), Direction.N),
            plateau = plateau
        )

        coEvery { roverRepository.getRoverInstructions() } returns flowOf(Result.success(input))

        // When
        val result = useCase()

        // Then
        result.collect { actualResult ->
            assertEquals(true, actualResult.isSuccess)
            val navigationResult = actualResult.getOrThrow()
            assertEquals(
                expectedResult.finalRover.currentPosition,
                navigationResult.finalRover.currentPosition
            )
            assertEquals(
                expectedResult.finalRover.currentDirection,
                navigationResult.finalRover.currentDirection
            )
            assertEquals(expectedResult.initialRover, navigationResult.initialRover)
            assertEquals(expectedResult.instructions, navigationResult.instructions)
        }
    }

    @Test
    fun `invoke should only rotate Rover with a single rotate right instruction`() = runTest {
        // Given
        val rover = Rover(Position(4, 3), Direction.S)
        val plateau = Plateau(Position(6, 4))
        val instructions = Instructions.fromString("R")
        val input = RoverInput(plateau, rover, instructions)
        val expectedResult = RoverNavigationResult(
            initialRover = rover,
            instructions = instructions,
            finalRover = Rover(Position(4, 3), Direction.W),
            plateau = plateau
        )

        coEvery { roverRepository.getRoverInstructions() } returns flowOf(Result.success(input))

        // When
        val result = useCase()

        // Then
        result.collect { actualResult ->
            assertEquals(true, actualResult.isSuccess)
            val navigationResult = actualResult.getOrThrow()
            assertEquals(
                expectedResult.finalRover.currentPosition,
                navigationResult.finalRover.currentPosition
            )
            assertEquals(
                expectedResult.finalRover.currentDirection,
                navigationResult.finalRover.currentDirection
            )
            assertEquals(expectedResult.initialRover, navigationResult.initialRover)
            assertEquals(expectedResult.instructions, navigationResult.instructions)
        }
    }

    @Test
    fun `invoke should only rotate Rover with a single rotate left instruction`() = runTest {
        // Given
        val rover = Rover(Position(4, 3), Direction.S)
        val plateau = Plateau(Position(6, 4))
        val instructions = Instructions.fromString("L")
        val input = RoverInput(plateau, rover, instructions)
        val expectedResult = RoverNavigationResult(
            initialRover = rover,
            instructions = instructions,
            finalRover = Rover(Position(4, 3), Direction.E),
            plateau = plateau
        )

        coEvery { roverRepository.getRoverInstructions() } returns flowOf(Result.success(input))

        // When
        val result = useCase()

        // Then
        result.collect { actualResult ->
            assertEquals(true, actualResult.isSuccess)
            val navigationResult = actualResult.getOrThrow()
            assertEquals(
                expectedResult.finalRover.currentPosition,
                navigationResult.finalRover.currentPosition
            )
            assertEquals(
                expectedResult.finalRover.currentDirection,
                navigationResult.finalRover.currentDirection
            )
            assertEquals(expectedResult.initialRover, navigationResult.initialRover)
            assertEquals(expectedResult.instructions, navigationResult.instructions)
        }
    }

    @Test
    fun `invoke should not move Rover when blocked by top bounds of plateau`() = runTest {
        // Given
        val rover = Rover(Position(3, 5), Direction.N)
        val plateau = Plateau(Position(5, 5))
        val instructions = Instructions.fromString("MM")
        val input = RoverInput(plateau, rover, instructions)
        val expectedResult = RoverNavigationResult(
            initialRover = rover,
            instructions = instructions,
            finalRover = Rover(Position(3, 5), Direction.N),
            plateau = plateau
        )

        coEvery { roverRepository.getRoverInstructions() } returns flowOf(Result.success(input))

        // When
        val result = useCase()

        // Then
        result.collect { actualResult ->
            assertEquals(true, actualResult.isSuccess)
            val navigationResult = actualResult.getOrThrow()
            assertEquals(
                expectedResult.finalRover.currentPosition,
                navigationResult.finalRover.currentPosition
            )
            assertEquals(
                expectedResult.finalRover.currentDirection,
                navigationResult.finalRover.currentDirection
            )
            assertEquals(expectedResult.initialRover, navigationResult.initialRover)
            assertEquals(expectedResult.instructions, navigationResult.instructions)
        }
    }

    @Test
    fun `invoke should not move Rover when blocked by bottom bounds of plateau`() = runTest {
        // Given
        val rover = Rover(Position(2, 0), Direction.S)
        val plateau = Plateau(Position(5, 5))
        val instructions = Instructions.fromString("MM")
        val input = RoverInput(plateau, rover, instructions)
        val expectedResult = RoverNavigationResult(
            initialRover = rover,
            instructions = instructions,
            finalRover = Rover(Position(2, 0), Direction.S),
            plateau = plateau
        )

        coEvery { roverRepository.getRoverInstructions() } returns flowOf(Result.success(input))

        // When
        val result = useCase()

        // Then
        result.collect { actualResult ->
            assertEquals(true, actualResult.isSuccess)
            val navigationResult = actualResult.getOrThrow()
            assertEquals(
                expectedResult.finalRover.currentPosition,
                navigationResult.finalRover.currentPosition
            )
            assertEquals(
                expectedResult.finalRover.currentDirection,
                navigationResult.finalRover.currentDirection
            )
            assertEquals(expectedResult.initialRover, navigationResult.initialRover)
            assertEquals(expectedResult.instructions, navigationResult.instructions)
        }
    }

    @Test
    fun `invoke should not move Rover when the instructions list is empty`() = runTest {
        // Given
        val rover = Rover(Position(2, 1), Direction.N)
        val plateau = Plateau(Position(5, 5))
        val instructions = Instructions.fromString("")
        val input = RoverInput(plateau, rover, instructions)
        val expectedResult = RoverNavigationResult(
            initialRover = rover,
            instructions = instructions,
            finalRover = Rover(Position(2, 1), Direction.N),
            plateau = plateau
        )

        coEvery { roverRepository.getRoverInstructions() } returns flowOf(Result.success(input))

        // When
        val result = useCase()

        // Then
        result.collect { actualResult ->
            assertEquals(true, actualResult.isSuccess)
            val navigationResult = actualResult.getOrThrow()
            assertEquals(
                expectedResult.finalRover.currentPosition,
                navigationResult.finalRover.currentPosition
            )
            assertEquals(
                expectedResult.finalRover.currentDirection,
                navigationResult.finalRover.currentDirection
            )
            assertEquals(expectedResult.initialRover, navigationResult.initialRover)
            assertEquals(expectedResult.instructions, navigationResult.instructions)
        }
    }

    @Test(expected = IllegalArgumentException::class)
    fun `invoke should return IllegalArgumentException when invalid commands are sent`() = runTest {
        // Given
        val rover = Rover(Position(4, 1), Direction.N)
        val plateau = Plateau(Position(4, 6))
        val instructions = Instructions.fromString("XXGGH")
        val input = RoverInput(plateau, rover, instructions)

        coEvery { roverRepository.getRoverInstructions() } returns flowOf(Result.success(input))

        // When
        val result = useCase()

        // Then
        result.collect { actualResult ->
            actualResult.getOrThrow()
        }
    }

    @Test(expected = IllegalArgumentException::class)
    fun `invoke should return IllegalArgumentException when invalid and valid commands are sent`() =
        runTest {
            // Given
            val rover = Rover(Position(4, 1), Direction.N)
            val plateau = Plateau(Position(4, 6))
        val instructions = Instructions.fromString("LRMMXMML")
            val input = RoverInput(plateau, rover, instructions)

            coEvery { roverRepository.getRoverInstructions() } returns flowOf(Result.success(input))

            // When
            val result = useCase()

            // Then
            result.collect { actualResult ->
                actualResult.getOrThrow()
            }
    }

    @Test
    fun `invoke should handle correctly single directions instructions by rotating Rover`() =
        runTest {
            // Given
            val rover = Rover(Position(2, 1), Direction.N)
            val plateau = Plateau(Position(5, 5))
        val instructions = Instructions.fromString("RRRR")
            val input = RoverInput(plateau, rover, instructions)
            val expectedResult = RoverNavigationResult(
                initialRover = rover,
                instructions = instructions,
                finalRover = Rover(Position(2, 1), Direction.N),
                plateau = plateau
            )

            coEvery { roverRepository.getRoverInstructions() } returns flowOf(Result.success(input))

            // When
            val result = useCase()

            // Then
            result.collect { actualResult ->
                assertEquals(true, actualResult.isSuccess)
                val navigationResult = actualResult.getOrThrow()
                assertEquals(
                    expectedResult.finalRover.currentPosition,
                    navigationResult.finalRover.currentPosition
                )
                assertEquals(
                    expectedResult.finalRover.currentDirection,
                    navigationResult.finalRover.currentDirection
                )
                assertEquals(expectedResult.initialRover, navigationResult.initialRover)
                assertEquals(expectedResult.instructions, navigationResult.instructions)
            }
    }

    @Test
    fun `invoke should handle correctly single movements instructions by moving Rover`() = runTest {
        // Given
        val rover = Rover(Position(2, 1), Direction.N)
        val plateau = Plateau(Position(5, 5))
        val instructions = Instructions.fromString("MMM")
        val input = RoverInput(plateau, rover, instructions)
        val expectedResult = RoverNavigationResult(
            initialRover = rover,
            instructions = instructions,
            finalRover = Rover(Position(2, 4), Direction.N),
            plateau = plateau
        )

        coEvery { roverRepository.getRoverInstructions() } returns flowOf(Result.success(input))

        // When
        val result = useCase()

        // Then
        result.collect { actualResult ->
            assertEquals(true, actualResult.isSuccess)
            val navigationResult = actualResult.getOrThrow()
            assertEquals(
                expectedResult.finalRover.currentPosition,
                navigationResult.finalRover.currentPosition
            )
            assertEquals(
                expectedResult.finalRover.currentDirection,
                navigationResult.finalRover.currentDirection
            )
            assertEquals(expectedResult.initialRover, navigationResult.initialRover)
            assertEquals(expectedResult.instructions, navigationResult.instructions)
        }
    }

    @Test
    fun `invoke should handle correctly moving over the bounds limits and stay on the border`() =
        runTest {
            // Given
            val rover = Rover(Position(2, 1), Direction.N)
            val plateau = Plateau(Position(5, 5))
        val instructions = Instructions.fromString("MMMMMMMMMMMMMM")
            val input = RoverInput(plateau, rover, instructions)
            val expectedResult = RoverNavigationResult(
                initialRover = rover,
                instructions = instructions,
                finalRover = Rover(Position(2, 5), Direction.N),
                plateau = plateau
            )

            coEvery { roverRepository.getRoverInstructions() } returns flowOf(Result.success(input))

            // When
            val result = useCase()

            // Then
            result.collect { actualResult ->
                assertEquals(true, actualResult.isSuccess)
                val navigationResult = actualResult.getOrThrow()
                assertEquals(
                    expectedResult.finalRover.currentPosition,
                    navigationResult.finalRover.currentPosition
                )
                assertEquals(
                    expectedResult.finalRover.currentDirection,
                    navigationResult.finalRover.currentDirection
                )
                assertEquals(expectedResult.initialRover, navigationResult.initialRover)
                assertEquals(expectedResult.instructions, navigationResult.instructions)
            }
    }

    @Test
    fun `invoke should handle correctly a 1x1 Plateau with movement instructions`() = runTest {
        // Given
        val rover = Rover(Position(0, 0), Direction.N)
        val plateau = Plateau(Position(1, 1))
        val instructions = Instructions.fromString("MM")
        val input = RoverInput(plateau, rover, instructions)
        val expectedResult = RoverNavigationResult(
            initialRover = rover,
            instructions = instructions,
            finalRover = Rover(Position(0, 1), Direction.N),
            plateau = plateau
        )

        coEvery { roverRepository.getRoverInstructions() } returns flowOf(Result.success(input))

        // When
        val result = useCase()

        // Then
        result.collect { actualResult ->
            assertEquals(true, actualResult.isSuccess)
            val navigationResult = actualResult.getOrThrow()
            assertEquals(
                expectedResult.finalRover.currentPosition,
                navigationResult.finalRover.currentPosition
            )
            assertEquals(
                expectedResult.finalRover.currentDirection,
                navigationResult.finalRover.currentDirection
            )
            assertEquals(expectedResult.initialRover, navigationResult.initialRover)
            assertEquals(expectedResult.instructions, navigationResult.instructions)
        }
    }

    @Test
    fun `invoke should handle correctly a 0x0 Plateau with movement instructions`() = runTest {
        // Given
        val rover = Rover(Position(0, 0), Direction.N)
        val plateau = Plateau(Position(0, 0))
        val instructions = Instructions.fromString("MM")
        val input = RoverInput(plateau, rover, instructions)
        val expectedResult = RoverNavigationResult(
            initialRover = rover,
            instructions = instructions,
            finalRover = Rover(Position(0, 0), Direction.N),
            plateau = plateau
        )

        coEvery { roverRepository.getRoverInstructions() } returns flowOf(Result.success(input))

        // When
        val result = useCase()

        // Then
        result.collect { actualResult ->
            assertEquals(true, actualResult.isSuccess)
            val navigationResult = actualResult.getOrThrow()
            assertEquals(
                expectedResult.finalRover.currentPosition,
                navigationResult.finalRover.currentPosition
            )
            assertEquals(
                expectedResult.finalRover.currentDirection,
                navigationResult.finalRover.currentDirection
            )
            assertEquals(expectedResult.initialRover, navigationResult.initialRover)
            assertEquals(expectedResult.instructions, navigationResult.instructions)
        }
    }

    @Test
    fun `invoke should handle correctly a 0x0 Plateau with direction instructions`() = runTest {
        // Given
        val rover = Rover(Position(0, 0), Direction.N)
        val plateau = Plateau(Position(0, 0))
        val instructions = Instructions.fromString("LL")
        val input = RoverInput(plateau, rover, instructions)
        val expectedResult = RoverNavigationResult(
            initialRover = rover,
            instructions = instructions,
            finalRover = Rover(Position(0, 0), Direction.S),
            plateau = plateau
        )

        coEvery { roverRepository.getRoverInstructions() } returns flowOf(Result.success(input))

        // When
        val result = useCase()

        // Then
        result.collect { actualResult ->
            assertEquals(true, actualResult.isSuccess)
            val navigationResult = actualResult.getOrThrow()
            assertEquals(
                expectedResult.finalRover.currentPosition,
                navigationResult.finalRover.currentPosition
            )
            assertEquals(
                expectedResult.finalRover.currentDirection,
                navigationResult.finalRover.currentDirection
            )
            assertEquals(expectedResult.initialRover, navigationResult.initialRover)
            assertEquals(expectedResult.instructions, navigationResult.instructions)
        }
    }
}
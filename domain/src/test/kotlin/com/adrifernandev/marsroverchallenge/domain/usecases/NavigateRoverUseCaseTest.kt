package com.adrifernandev.marsroverchallenge.domain.usecases

import com.adrifernandev.marsroverchallenge.domain.models.Direction
import com.adrifernandev.marsroverchallenge.domain.models.Instructions
import com.adrifernandev.marsroverchallenge.domain.models.Plateau
import com.adrifernandev.marsroverchallenge.domain.models.Position
import com.adrifernandev.marsroverchallenge.domain.models.Rover
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class NavigateRoverUseCaseTest {

    private lateinit var useCase: NavigateRoverUseCase

    @Before
    fun setUp() {
        useCase = NavigateRoverUseCase()
    }

    @Test
    fun `invoke should return moved Rover with valid instructions`() {
        // Given
        val rover = Rover(
            currentPosition = Position(x = 1, y = 2),
            currentDirection = Direction.N
        )
        val plateau = Plateau(topRightCornerPosition = Position(x = 5, y = 5))
        val instructions = Instructions.fromString("LMLMLMLMM")

        //When
        val result = useCase(rover = rover, plateau = plateau, instructions = instructions)

        //Then
        assertEquals(Position(x = 1, y = 3), result.currentPosition)
        assertEquals(Direction.N, result.currentDirection)
    }

    @Test
    fun `Invoke should only rotate Rover with a single rotate right instruction`() {
        //Given
        val rover = Rover(
            currentPosition = Position(x = 4, y = 3),
            currentDirection = Direction.S
        )
        val plateau = Plateau(topRightCornerPosition = Position(x = 6, y = 4))
        val instructions = Instructions.fromString("R")

        //When
        val result = useCase(rover = rover, plateau = plateau, instructions = instructions)

        //Then
        assertEquals(Position(x = 4, y = 3), result.currentPosition)
        assertEquals(Direction.W, result.currentDirection)
    }

    @Test
    fun `Invoke should only rotate Rover with a single rotate left instruction`() {
        //Given
        val rover = Rover(
            currentPosition = Position(x = 4, y = 3),
            currentDirection = Direction.S
        )
        val plateau = Plateau(topRightCornerPosition = Position(x = 6, y = 4))
        val instructions = Instructions.fromString("L")

        //When
        val result = useCase(rover = rover, plateau = plateau, instructions = instructions)

        //Then
        assertEquals(Position(x = 4, y = 3), result.currentPosition)
        assertEquals(Direction.E, result.currentDirection)
    }

    @Test
    fun `Invoke should not move Rover when this is blocked by top bounds of plateau`() {
        //Given
        val rover = Rover(
            currentPosition = Position(x = 3, y = 5),
            currentDirection = Direction.N
        )
        val plateau = Plateau(topRightCornerPosition = Position(x = 5, y = 5))
        val instructions = Instructions.fromString("MM")

        //When
        val result = useCase(rover = rover, plateau = plateau, instructions = instructions)

        //Then
        assertEquals(Position(x = 3, y = 5), result.currentPosition)
        assertEquals(Direction.N, result.currentDirection)
    }

    @Test
    fun `Invoke should not move Rover when this is blocked by bottom bounds of plateau`() {
        //Given
        val rover = Rover(
            currentPosition = Position(x = 2, y = 0),
            currentDirection = Direction.S
        )
        val plateau = Plateau(topRightCornerPosition = Position(x = 5, y = 5))
        val instructions = Instructions.fromString("MM")

        //When
        val result = useCase(rover = rover, plateau = plateau, instructions = instructions)

        //Then
        assertEquals(Position(x = 2, y = 0), result.currentPosition)
        assertEquals(Direction.S, result.currentDirection)
    }

    @Test
    fun `Invoke should not move Rover when the instructions list is empty`() {
        //Given
        val rover = Rover(
            currentPosition = Position(x = 2, y = 1),
            currentDirection = Direction.N
        )
        val plateau = Plateau(topRightCornerPosition = Position(x = 5, y = 5))
        val instructions = Instructions.fromString("")

        //When
        val result = useCase(rover = rover, plateau = plateau, instructions = instructions)

        //Then
        assertEquals(Position(x = 2, y = 1), result.currentPosition)
        assertEquals(Direction.N, result.currentDirection)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `Invoke should return IllegalArgumentException when invalid commands are sent`() {
        //Given
        val rover = Rover(
            currentPosition = Position(x = 4, y = 1),
            currentDirection = Direction.N
        )
        val plateau = Plateau(topRightCornerPosition = Position(x = 4, y = 6))
        val instructions = Instructions.fromString("XXGGH")
        useCase(rover = rover, plateau = plateau, instructions = instructions)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `Invoke should return IllegalArgumentException when invalid and valid commands are sent`() {
        //Given
        val rover = Rover(
            currentPosition = Position(x = 4, y = 1),
            currentDirection = Direction.N
        )
        val plateau = Plateau(topRightCornerPosition = Position(x = 4, y = 6))
        val instructions = Instructions.fromString("LRMMXMML")
        useCase(rover = rover, plateau = plateau, instructions = instructions)
    }

    @Test
    fun `Invoke should handle correctly single directions instructions by rotating Rover`() {
        //Given
        val rover = Rover(
            currentPosition = Position(x = 2, y = 1),
            currentDirection = Direction.N
        )
        val plateau = Plateau(topRightCornerPosition = Position(x = 5, y = 5))
        val instructions = Instructions.fromString("RRRR")

        //When
        val result = useCase(rover = rover, plateau = plateau, instructions = instructions)

        //Then
        assertEquals(Position(x = 2, y = 1), result.currentPosition)
        assertEquals(Direction.N, result.currentDirection)
    }

    @Test
    fun `Invoke should handle correctly single movements instructions by moving Rover`() {
        //Given
        val rover = Rover(
            currentPosition = Position(x = 2, y = 1),
            currentDirection = Direction.N
        )
        val plateau = Plateau(topRightCornerPosition = Position(x = 5, y = 5))
        val instructions = Instructions.fromString("MMM")

        //When
        val result = useCase(rover = rover, plateau = plateau, instructions = instructions)

        //Then
        assertEquals(Position(x = 2, y = 4), result.currentPosition)
        assertEquals(Direction.N, result.currentDirection)
    }

    @Test
    fun `Invoke should handle correctly moving over the bounds limits and stay on the border`() {
        //Given
        val rover = Rover(
            currentPosition = Position(x = 2, y = 1),
            currentDirection = Direction.N
        )
        val plateau = Plateau(topRightCornerPosition = Position(x = 5, y = 5))
        val instructions = Instructions.fromString("MMMMMMMMMMMMMM")

        //When
        val result = useCase(rover = rover, plateau = plateau, instructions = instructions)

        //Then
        assertEquals(Position(x = 2, y = 5), result.currentPosition)
        assertEquals(Direction.N, result.currentDirection)
    }

    @Test
    fun `Invoke should handle correctly a 1x1 Plateau with movement instructions`() {
        //Given
        val rover = Rover(
            currentPosition = Position(x = 0, y = 0),
            currentDirection = Direction.N
        )
        val plateau = Plateau(topRightCornerPosition = Position(x = 1, y = 1))
        val instructions = Instructions.fromString("MM")

        //When
        val result = useCase(rover = rover, plateau = plateau, instructions = instructions)

        //Then
        assertEquals(Position(x = 0, y = 1), result.currentPosition)
        assertEquals(Direction.N, result.currentDirection)
    }

    @Test
    fun `Invoke should handle correctly a 0x0 Plateau with movement instructions`() {
        //Given
        val rover = Rover(
            currentPosition = Position(x = 0, y = 0),
            currentDirection = Direction.N
        )
        val plateau = Plateau(topRightCornerPosition = Position(x = 0, y = 0))
        val instructions = Instructions.fromString("MM")

        //When
        val result = useCase(rover = rover, plateau = plateau, instructions = instructions)

        //Then
        assertEquals(Position(x = 0, y = 0), result.currentPosition)
        assertEquals(Direction.N, result.currentDirection)
    }

    @Test
    fun `Invoke should handle correctly a 0x0 Plateau with direction instructions`() {
        //Given
        val rover = Rover(
            currentPosition = Position(x = 0, y = 0),
            currentDirection = Direction.N
        )
        val plateau = Plateau(topRightCornerPosition = Position(x = 0, y = 0))
        val instructions = Instructions.fromString("LL")

        //When
        val result = useCase(rover = rover, plateau = plateau, instructions = instructions)

        //Then
        assertEquals(Position(x = 0, y = 0), result.currentPosition)
        assertEquals(Direction.S, result.currentDirection)
    }
}
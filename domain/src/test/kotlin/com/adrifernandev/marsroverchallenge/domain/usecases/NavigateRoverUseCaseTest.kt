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
}
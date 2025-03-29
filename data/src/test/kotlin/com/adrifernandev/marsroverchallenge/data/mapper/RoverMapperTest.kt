package com.adrifernandev.marsroverchallenge.data.mapper

import com.adrifernandev.marsroverchallenge.data.dto.PositionDTO
import com.adrifernandev.marsroverchallenge.data.dto.RoverInstructionsDTO
import com.adrifernandev.marsroverchallenge.domain.models.Direction
import com.adrifernandev.marsroverchallenge.domain.models.Instructions
import com.adrifernandev.marsroverchallenge.domain.models.Plateau
import com.adrifernandev.marsroverchallenge.domain.models.Position
import com.adrifernandev.marsroverchallenge.domain.models.Rover
import com.adrifernandev.marsroverchallenge.domain.models.RoverInput
import org.junit.Assert.assertEquals
import org.junit.Test
import kotlin.test.assertFailsWith

class RoverMapperTest {

    @Test
    fun `toDomain should map RoverInstructionsDTO to RoverInput correctly`() {
        // Given
        val dto = RoverInstructionsDTO(
            topRightCorner = PositionDTO(x = 5, y = 5),
            roverPosition = PositionDTO(x = 1, y = 2),
            roverDirection = "N",
            movements = "LMLMLMLMM"
        )
        val expected = RoverInput(
            plateau = Plateau(Position(5, 5)),
            initialRover = Rover(Position(1, 2), Direction.N),
            instructions = Instructions.fromString("LMLMLMLMM")
        )

        // When
        val result = dto.toDomain()

        // Then
        assertEquals(expected, result)
    }

    @Test
    fun `toPlateau should throw IllegalArgumentException for negative dimensions`() {
        // Given
        val dto = PositionDTO(x = -1, y = 5)

        // When
        val exception = assertFailsWith<IllegalArgumentException> {
            dto.toPlateau()
        }

        // Then
        assertEquals("Plateau dimensions must be non-negative", exception.message)
    }

    @Test
    fun `toRover should map PositionDTO and direction correctly`() {
        // Given
        val positionDto = PositionDTO(x = 3, y = 4)
        val direction = "E"
        val expected = Rover(Position(3, 4), Direction.E)

        // When
        val result = toRover(positionDto, direction)

        // Then
        assertEquals(expected, result)
    }

    @Test
    fun `toRover should throw IllegalArgumentException for negative position`() {
        // Given
        val positionDto = PositionDTO(x = -1, y = 2)
        val direction = "N"

        // When
        val exception = assertFailsWith<IllegalArgumentException> {
            toRover(positionDto, direction)
        }

        // Then
        assertEquals("Rover position must be non-negative", exception.message)
    }

    @Test
    fun `toRover should throw IllegalArgumentException for invalid direction`() {
        // Given
        val positionDto = PositionDTO(x = 1, y = 2)
        val direction = "X"

        // When
        val exception = assertFailsWith<IllegalArgumentException> {
            toRover(positionDto, direction)
        }

        // Then
        assertEquals("Invalid rover direction: X", exception.message)
    }
}
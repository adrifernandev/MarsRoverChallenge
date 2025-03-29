package com.adrifernandev.marsroverchallenge.data.mapper

import com.adrifernandev.marsroverchallenge.data.dto.PositionDTO
import com.adrifernandev.marsroverchallenge.data.dto.RoverInstructionsDTO
import com.adrifernandev.marsroverchallenge.domain.models.Direction
import com.adrifernandev.marsroverchallenge.domain.models.Instructions
import com.adrifernandev.marsroverchallenge.domain.models.Plateau
import com.adrifernandev.marsroverchallenge.domain.models.Position
import com.adrifernandev.marsroverchallenge.domain.models.Rover
import com.adrifernandev.marsroverchallenge.domain.models.RoverInput

fun RoverInstructionsDTO.toDomain(): RoverInput {
    return try {
        RoverInput(
            plateau = this.topRightCorner.toPlateau(),
            initialRover = toRover(this.roverPosition, this.roverDirection),
            instructions = Instructions.fromString(this.movements)
        )
    } catch (e: IllegalArgumentException) {
        throw IllegalArgumentException("Error while mapping to domain model: ${e.message}")
    }
}

fun PositionDTO.toPlateau(): Plateau {
    require(this.x >= 0 && this.y >= 0) { "Plateau dimensions must be non-negative" }
    return Plateau(Position(this.x, this.y))
}

fun toRover(positionDTO: PositionDTO, direction: String): Rover {
    require(positionDTO.x >= 0 && positionDTO.y >= 0) { "Rover position must be non-negative" }
    return Rover(
        currentPosition = Position(positionDTO.x, positionDTO.y),
        currentDirection = try {
            Direction.valueOf(direction)
        } catch (e: IllegalArgumentException) {
            throw IllegalArgumentException("Invalid rover direction: $direction")
        }
    )
}
package com.adrifernandev.marsroverchallenge.domain.usecases

import com.adrifernandev.marsroverchallenge.domain.models.Direction
import com.adrifernandev.marsroverchallenge.domain.models.Instruction
import com.adrifernandev.marsroverchallenge.domain.models.Instructions
import com.adrifernandev.marsroverchallenge.domain.models.Plateau
import com.adrifernandev.marsroverchallenge.domain.models.Position
import com.adrifernandev.marsroverchallenge.domain.models.Rover
import com.adrifernandev.marsroverchallenge.domain.models.RoverInput
import com.adrifernandev.marsroverchallenge.domain.models.RoverNavigationResult

class NavigateRoverUseCase {

    operator fun invoke(): RoverNavigationResult {
        val input = RoverInput(
            plateau = Plateau(
                topRightCornerPosition = Position(
                    x = 5,
                    y = 5
                )
            ),
            initialRover = Rover(
                currentPosition = Position(
                    x = 1,
                    y = 2
                ),
                currentDirection = Direction.N
            ),
            instructions = Instructions.fromString("LMLMLMLMM")
        ) //TODO: Currently mocked for testing purposes, replace with Repository

        var currentRover = input.initialRover

        input.instructions.commands.forEach { instruction ->
            currentRover = when (instruction) {
                is Instruction.RotateLeft -> rotateLeft(currentRover)
                is Instruction.RotateRight -> rotateRight(currentRover)
                is Instruction.MoveForward -> moveForward(currentRover, input.plateau)
            }
        }

        return RoverNavigationResult(
            initialRover = input.initialRover,
            instructions = input.instructions,
            finalRover = currentRover
        )
    }

    private fun rotateLeft(rover: Rover): Rover {
        val newDirection = when (rover.currentDirection) {
            Direction.N -> Direction.W
            Direction.W -> Direction.S
            Direction.S -> Direction.E
            Direction.E -> Direction.N
        }
        return rover.copy(currentDirection = newDirection)
    }

    private fun rotateRight(rover: Rover): Rover {
        val newDirection = when (rover.currentDirection) {
            Direction.N -> Direction.E
            Direction.E -> Direction.S
            Direction.S -> Direction.W
            Direction.W -> Direction.N
        }
        return rover.copy(currentDirection = newDirection)
    }

    private fun moveForward(rover: Rover, plateau: Plateau): Rover {
        val newPosition = when (rover.currentDirection) {
            Direction.N -> rover.currentPosition.copy(y = rover.currentPosition.y + 1)
            Direction.W -> rover.currentPosition.copy(x = rover.currentPosition.x - 1)
            Direction.S -> rover.currentPosition.copy(y = rover.currentPosition.y - 1)
            Direction.E -> rover.currentPosition.copy(x = rover.currentPosition.x + 1)
        }
        val isRoverAbleToMove = isWithinPlateauBounds(newPosition, plateau)
        return if (isRoverAbleToMove) {
            rover.copy(currentPosition = newPosition)
        } else {
            rover
        }
    }

    private fun isWithinPlateauBounds(position: Position, plateau: Plateau): Boolean {
        return position.x >= 0 && position.y >= 0 &&
                position.x <= plateau.topRightCornerPosition.x &&
                position.y <= plateau.topRightCornerPosition.y
    }
}
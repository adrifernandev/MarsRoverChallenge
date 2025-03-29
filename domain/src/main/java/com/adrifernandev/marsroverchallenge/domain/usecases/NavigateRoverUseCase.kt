package com.adrifernandev.marsroverchallenge.domain.usecases

import com.adrifernandev.marsroverchallenge.domain.models.Direction
import com.adrifernandev.marsroverchallenge.domain.models.Instruction
import com.adrifernandev.marsroverchallenge.domain.models.Plateau
import com.adrifernandev.marsroverchallenge.domain.models.Position
import com.adrifernandev.marsroverchallenge.domain.models.Rover
import com.adrifernandev.marsroverchallenge.domain.models.RoverNavigationResult
import com.adrifernandev.marsroverchallenge.domain.repository.RoverRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

class NavigateRoverUseCase @Inject constructor(
    private val roverRepository: RoverRepository
) {

    operator fun invoke(): Flow<Result<RoverNavigationResult>> = channelFlow {
        roverRepository.getRoverInstructions().collectLatest {
            it.fold(
                onSuccess = { roverInput ->
                    var currentRover = roverInput.initialRover
                    roverInput.instructions.commands.forEach { instruction ->
                        currentRover = when (instruction) {
                            is Instruction.RotateLeft -> rotateLeft(currentRover)
                            is Instruction.RotateRight -> rotateRight(currentRover)
                            is Instruction.MoveForward -> moveForward(
                                currentRover,
                                roverInput.plateau
                            )
                        }
                    }
                    send(
                        Result.success(
                            RoverNavigationResult(
                                initialRover = roverInput.initialRover,
                                instructions = roverInput.instructions,
                                finalRover = currentRover,
                                plateau = roverInput.plateau
                            )
                        )
                    )
                },
                onFailure = { error ->
                    send(Result.failure(error))
                }
            )
        }
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
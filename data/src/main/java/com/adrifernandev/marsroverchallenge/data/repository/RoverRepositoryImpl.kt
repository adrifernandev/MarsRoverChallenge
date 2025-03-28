package com.adrifernandev.marsroverchallenge.data.repository

import com.adrifernandev.marsroverchallenge.domain.models.Direction
import com.adrifernandev.marsroverchallenge.domain.models.Instructions
import com.adrifernandev.marsroverchallenge.domain.models.Plateau
import com.adrifernandev.marsroverchallenge.domain.models.Position
import com.adrifernandev.marsroverchallenge.domain.models.Rover
import com.adrifernandev.marsroverchallenge.domain.models.RoverInput
import com.adrifernandev.marsroverchallenge.domain.repository.RoverRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow

class RoverRepositoryImpl : RoverRepository {

    override fun getRoverInstructions(): Flow<Result<RoverInput>> = channelFlow {
        delay(1000) //TODO: Simulating network delay
        send(
            Result.success(
                RoverInput(
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
                )
            )
        )
    }
}
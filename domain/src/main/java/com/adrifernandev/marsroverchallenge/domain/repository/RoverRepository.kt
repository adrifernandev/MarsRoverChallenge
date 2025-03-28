package com.adrifernandev.marsroverchallenge.domain.repository

import com.adrifernandev.marsroverchallenge.domain.models.RoverInput
import kotlinx.coroutines.flow.Flow

interface RoverRepository {
    fun getRoverInstructions(): Flow<Result<RoverInput>>
}
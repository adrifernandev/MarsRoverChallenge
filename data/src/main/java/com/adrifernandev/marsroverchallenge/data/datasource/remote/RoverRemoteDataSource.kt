package com.adrifernandev.marsroverchallenge.data.datasource.remote

import com.adrifernandev.marsroverchallenge.data.dto.RoverInstructionsDTO
import kotlinx.coroutines.flow.Flow

interface RoverRemoteDataSource {
    fun getRoverInput(): Flow<Result<RoverInstructionsDTO>>
}
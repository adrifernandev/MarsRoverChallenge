package com.adrifernandev.marsroverchallenge.data.datasource.remote

import com.adrifernandev.marsroverchallenge.data.datasource.remote.service.RoverService
import com.adrifernandev.marsroverchallenge.data.dto.RoverInstructionsDTO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RoverRemoteDataSourceImpl @Inject constructor(
    private val roverService: RoverService
) : RoverRemoteDataSource {

    override fun getRoverInput(): Flow<Result<RoverInstructionsDTO>> = flow {
        try {
            val dto = roverService.getRoverInput()
            emit(Result.success(dto))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }
}
package com.adrifernandev.marsroverchallenge.data.repository

import com.adrifernandev.marsroverchallenge.data.datasource.remote.RoverRemoteDataSource
import com.adrifernandev.marsroverchallenge.data.mapper.toDomain
import com.adrifernandev.marsroverchallenge.domain.models.RoverInput
import com.adrifernandev.marsroverchallenge.domain.repository.RoverRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

class RoverRepositoryImpl @Inject constructor(
    private val roverRemoteDataSource: RoverRemoteDataSource
) : RoverRepository {

    override fun getRoverInstructions(): Flow<Result<RoverInput>> = channelFlow {
        roverRemoteDataSource.getRoverInput()
            .collectLatest { roverInputResult ->
                roverInputResult.fold(
                    onSuccess = { roverInput ->
                        try {
                            val roverInputDomain = roverInput.toDomain()
                            send(Result.success(roverInputDomain))
                        } catch (e: IllegalArgumentException) {
                            send(Result.failure(e))
                        }
                    },
                    onFailure = { error ->
                        send(Result.failure(error))
                    }
                )
            }
    }
}
package com.adrifernandev.marsroverchallenge.data.datasource.remote

import app.cash.turbine.test
import com.adrifernandev.marsroverchallenge.data.datasource.remote.service.RoverService
import com.adrifernandev.marsroverchallenge.data.dto.PositionDTO
import com.adrifernandev.marsroverchallenge.data.dto.RoverInstructionsDTO
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class RoverRemoteDataSourceImplTest {

    private val roverService: RoverService = mockk()
    private val dataSource = RoverRemoteDataSourceImpl(roverService)

    @Test
    fun `getRoverInput should emit success when service returns valid DTO`() = runTest {
        // Given
        val dto = RoverInstructionsDTO(
            topRightCorner = PositionDTO(x = 5, y = 5),
            roverPosition = PositionDTO(x = 1, y = 2),
            roverDirection = "N",
            movements = "LMLMLMLMM"
        )
        coEvery { roverService.getRoverInput() } returns dto

        // When
        dataSource.getRoverInput().test {
            // Then
            val result = awaitItem()
            assertEquals(Result.success(dto), result)
            awaitComplete()
        }
    }

    @Test
    fun `getRoverInput should emit failure when service throws exception`() = runTest {
        // Given
        val exception = RuntimeException("Service failed")
        coEvery { roverService.getRoverInput() } throws exception

        // When
        dataSource.getRoverInput().test {
            // Then
            val result = awaitItem()
            assertEquals(Result.failure<RoverInstructionsDTO>(exception), result)
            awaitComplete()
        }
    }
}
package com.adrifernandev.marsroverchallenge.data.datasource.remote.service

import com.adrifernandev.marsroverchallenge.data.datasource.remote.service.generator.generateRandomRoverInputJson
import com.adrifernandev.marsroverchallenge.data.dto.RoverInstructionsDTO
import kotlinx.coroutines.delay
import kotlinx.serialization.json.Json
import javax.inject.Inject

private const val MIN_DELAY_TIME_LONG = 500L
private const val MAX_DELAY_TIME_LONG = 2000L

class RoverServiceImpl @Inject constructor(
    private val json: Json,
) : RoverService {

    override suspend fun getRoverInput(): RoverInstructionsDTO {
        val randomDelay = (MIN_DELAY_TIME_LONG..MAX_DELAY_TIME_LONG).random()
        delay(randomDelay)
        return try {
            val jsonInput = generateRandomRoverInputJson()
            json.decodeFromString<RoverInstructionsDTO>(jsonInput)
        } catch (e: Exception) {
            throw e
        }
    }
}
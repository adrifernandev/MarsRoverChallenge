package com.adrifernandev.marsroverchallenge.data.datasource.remote.service

import com.adrifernandev.marsroverchallenge.data.dto.RoverInstructionsDTO

interface RoverService {
    suspend fun getRoverInput(): RoverInstructionsDTO
}
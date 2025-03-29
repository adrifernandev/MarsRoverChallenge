package com.adrifernandev.marsroverchallenge.data.dto

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class RoverInstructionsDTO(
    @SerializedName("topRightCorner")
    val topRightCorner: PositionDTO,
    @SerializedName("roverPosition")
    val roverPosition: PositionDTO,
    @SerializedName("roverDirection")
    val roverDirection: String,
    @SerializedName("movements")
    val movements: String,
)

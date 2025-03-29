package com.adrifernandev.marsroverchallenge.data.dto

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class PositionDTO(
    @SerializedName("x")
    val x: Int,
    @SerializedName("y")
    val y: Int
)

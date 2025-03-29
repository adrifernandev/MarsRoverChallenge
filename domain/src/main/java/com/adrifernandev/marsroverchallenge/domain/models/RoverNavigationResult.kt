package com.adrifernandev.marsroverchallenge.domain.models

data class RoverNavigationResult(
    val initialRover: Rover,
    val instructions: Instructions,
    val finalRover: Rover,
    val plateau: Plateau
)

package com.adrifernandev.marsroverchallenge.domain.models

data class RoverInput(
    val plateau: Plateau,
    val initialRover: Rover,
    val instructions: Instructions
)

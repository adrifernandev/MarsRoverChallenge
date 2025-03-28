package com.adrifernandev.marsroverchallenge.domain.model

sealed class Instruction {
    data object RotateLeft : Instruction()
    data object RotateRight : Instruction()
    data object MoveForward : Instruction()

    companion object {
        fun fromChar(instruction: Char): Instruction {
            return when (instruction) {
                'L' -> RotateLeft
                'R' -> RotateRight
                'M' -> MoveForward
                else -> throw IllegalArgumentException("Invalid instruction")
            }
        }
    }
}

data class Instructions(val commands: List<Instruction>) {
    companion object {
        fun fromString(instructions: String): Instructions {
            return Instructions(instructions.map { Instruction.fromChar(it) })
        }
    }
}
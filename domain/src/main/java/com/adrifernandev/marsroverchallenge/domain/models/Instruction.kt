package com.adrifernandev.marsroverchallenge.domain.models

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
            val validInstructions = mutableListOf<Instruction>()
            instructions.forEach { char ->
                try {
                    validInstructions.add(Instruction.fromChar(char))
                } catch (e: IllegalArgumentException) {
                    throw e
                }
            }
            return Instructions(validInstructions)
        }
    }
}

fun Instructions.toCommandString(): String {
    return commands.joinToString("") { instruction ->
        when (instruction) {
            is Instruction.RotateLeft -> "L"
            is Instruction.RotateRight -> "R"
            is Instruction.MoveForward -> "M"
        }
    }
}
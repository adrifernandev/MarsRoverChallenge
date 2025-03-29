package com.adrifernandev.marsroverchallenge.data.datasource.remote.service.generator

import com.adrifernandev.marsroverchallenge.domain.models.Direction

private const val MIN_POSITION = 0
private const val MIN_TOP = 4
private const val MAX_TOP = 8
private const val MIN_MOVEMENTS = 5
private const val MAX_MOVEMENTS = 11

fun generateRandomRoverInputJson(): String {
    val maxX = (MIN_TOP..MAX_TOP).random()
    val maxY = (MIN_TOP..MAX_TOP).random()
    val startX = (MIN_POSITION..maxX).random()
    val startY = (MIN_POSITION..maxY).random()
    val direction = Direction.entries.toTypedArray().random().name
    val movements = generateRandomMovements()

    return """
            {
                "topRightCorner": {
                    "x": $maxX,
                    "y": $maxY
                },
                "roverPosition": {
                    "x": $startX,
                    "y": $startY
                },
                "roverDirection": "$direction",
                "movements": "$movements"
            }
        """.trimIndent()
}

private fun generateRandomMovements(): String {
    val length = (MIN_MOVEMENTS..MAX_MOVEMENTS).random()
    return List(length) {
        when ((0..3).random()) {
            0 -> "L"
            1 -> "R"
            else -> "M"
        }
    }.joinToString("")
}
package com.adrifernandev.marsroverchallenge.data.datasource.remote.service.generator

import com.adrifernandev.marsroverchallenge.domain.models.Direction
import kotlin.random.Random

fun generateRandomRoverInputJson(): String {
    val maxX = Random.nextInt(5, 11)
    val maxY = Random.nextInt(5, 11)
    val startX = Random.nextInt(0, maxX + 1)
    val startY = Random.nextInt(0, maxY + 1)
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
    val length = Random.nextInt(5, 11)
    return List(length) {
        when (Random.nextInt(0, 3)) {
            0 -> "L"
            1 -> "R"
            else -> "M"
        }
    }.joinToString("")
}
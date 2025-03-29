package com.adrifernandev.marsroverchallenge.presentation.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import com.adrifernandev.marsroverchallenge.domain.models.Plateau
import com.adrifernandev.marsroverchallenge.domain.models.Position

@Composable
fun PlateauGrid(
    modifier: Modifier = Modifier,
    plateau: Plateau? = null,
    initialRoverPosition: Position? = null,
    finalRoverPosition: Position? = null
) {
    val dotColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
    val roverColor = MaterialTheme.colorScheme.secondary
    val firstRoverPositionColor = MaterialTheme.colorScheme.primary

    plateau?.let {
        val width = plateau.topRightCornerPosition.x + 1
        val height = plateau.topRightCornerPosition.y + 1

        Canvas(modifier = modifier) {
            val cellSize = size.width / width.coerceAtLeast(1)
            val dotRadius = cellSize * 0.1f
            val roverRadius = cellSize * 0.3f

            for (x in 0 until width) {
                for (y in 0 until height) {
                    drawCircle(
                        color = dotColor,
                        radius = dotRadius,
                        center = Offset(
                            x = x * cellSize + cellSize / 2,
                            y = (height - 1 - y) * cellSize + cellSize / 2
                        )
                    )
                }
            }

            initialRoverPosition?.let { pos ->
                val center = Offset(
                    x = pos.x * cellSize + cellSize / 2,
                    y = (height - 1 - pos.y) * cellSize + cellSize / 2
                )
                drawLine(
                    color = firstRoverPositionColor,
                    start = Offset(center.x - roverRadius, center.y),
                    end = Offset(center.x + roverRadius, center.y),
                    strokeWidth = roverRadius / 2
                )
                drawLine(
                    color = firstRoverPositionColor,
                    start = Offset(center.x, center.y - roverRadius),
                    end = Offset(center.x, center.y + roverRadius),
                    strokeWidth = roverRadius / 2
                )
            }

            finalRoverPosition?.let { pos ->
                drawCircle(
                    color = roverColor,
                    radius = roverRadius,
                    center = Offset(
                        x = pos.x * cellSize + cellSize / 2,
                        y = (height - 1 - pos.y) * cellSize + cellSize / 2
                    )
                )
            }
        }
    }
}
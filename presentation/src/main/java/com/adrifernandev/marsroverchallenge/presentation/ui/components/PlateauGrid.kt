package com.adrifernandev.marsroverchallenge.presentation.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.rotate
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import com.adrifernandev.marsroverchallenge.domain.models.Direction
import com.adrifernandev.marsroverchallenge.domain.models.Plateau
import com.adrifernandev.marsroverchallenge.domain.models.Position
import com.adrifernandev.marsroverchallenge.presentation.R

@Composable
fun PlateauGrid(
    modifier: Modifier = Modifier,
    plateau: Plateau? = null,
    initialRoverPosition: Position? = null,
    initialRoverDirection: Direction? = null,
    finalRoverPosition: Position? = null,
    finalRoverDirection: Direction? = null,
) {
    val dotColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
    val roverPainter = painterResource(id = R.drawable.img_seat_ibiza_transparent)
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
                drawIntoCanvas { canvas ->
                    canvas.save()
                    canvas.translate(center.x, center.y)
                    initialRoverDirection?.let { direction ->
                        val rotation = when (direction) {
                            Direction.N -> 0f
                            Direction.E -> 90f
                            Direction.S -> 180f
                            Direction.W -> 270f
                        }
                        canvas.rotate(rotation)
                    }
                    val path = Path().apply {
                        moveTo(0f, -roverRadius)
                        lineTo(roverRadius, roverRadius)
                        lineTo(-roverRadius, roverRadius)
                        close()
                    }
                    canvas.nativeCanvas.drawPath(
                        path.asAndroidPath(),
                        android.graphics.Paint().apply {
                            color = firstRoverPositionColor.toArgb()
                            style = android.graphics.Paint.Style.FILL
                        })
                    canvas.restore()
                }
            }

            finalRoverPosition?.let { pos ->
                val center = Offset(
                    x = pos.x * cellSize + cellSize / 2,
                    y = (height - 1 - pos.y) * cellSize + cellSize / 2
                )
                drawIntoCanvas { canvas ->
                    canvas.save()

                    val intrinsicWidth = roverPainter.intrinsicSize.width
                    val intrinsicHeight = roverPainter.intrinsicSize.height
                    val scale = (cellSize * 1.2f) / maxOf(intrinsicWidth, intrinsicHeight)
                    val scaledWidth = intrinsicWidth * scale
                    val scaledHeight = intrinsicHeight * scale

                    canvas.translate(center.x - scaledWidth / 2, center.y - scaledHeight / 2)
                    finalRoverDirection?.let { direction ->
                        val rotation = when (direction) {
                            Direction.N -> 0f
                            Direction.E -> 90f
                            Direction.S -> 180f
                            Direction.W -> 270f
                        }
                        canvas.rotate(rotation, scaledWidth / 2, scaledHeight / 2)
                    }
                    with(roverPainter) {
                        draw(
                            size = Size(scaledWidth, scaledHeight),
                        )
                    }
                    canvas.restore()
                }
            }
        }
    }
}
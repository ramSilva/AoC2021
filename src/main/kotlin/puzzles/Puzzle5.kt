package puzzles

import java.io.File
import kotlin.math.absoluteValue
import kotlin.math.max
import kotlin.math.sign

private val lines = File("input/puzzle5/input.txt").readLines()

fun puzzle5(diagonals: Boolean = false): Int {
    val grid = mutableMapOf<Pair<Int, Int>, Int>()

    lines.forEach {
        val coordinates = it.split(" ")
        val firstCoordinate = coordinates[0].split(",").map { it.toInt() }
        val secondCoordinate = coordinates[2].split(",").map { it.toInt() }

        val xIncline = (firstCoordinate[0] - secondCoordinate[0]).sign
        val yIncline = (firstCoordinate[1] - secondCoordinate[1]).sign

        val h = max(
            (firstCoordinate[0] - secondCoordinate[0]).absoluteValue,
            (firstCoordinate[1] - secondCoordinate[1]).absoluteValue
        )

        for (i in 0..h) {
            if (!diagonals && xIncline != 0 && yIncline != 0) break
            val currentCoordinate = Pair(firstCoordinate[0] + i * -xIncline, firstCoordinate[1] + i * -yIncline)
            grid[currentCoordinate] =
                (grid[currentCoordinate] ?: 0).plus(1) // Put a 1 if it's a new value or add 1 if it already exists
        }
    }

    return grid.count { it.value > 1 }
}

package puzzles

import java.io.File
import kotlin.math.absoluteValue
import kotlin.math.sign

private val lines = File("input/puzzle5/input.txt").readLines()
//private val lines = File("input/puzzle5/testInput.txt").readLines()

fun puzzle5(): Int {
    val grid = mutableMapOf<Pair<Int, Int>, Int>()
    lines.forEach {
        val coordinates = it.split(" ")
        val firstCoordinate = coordinates[0].split(",").map { it.toInt() }
        val secondCoordinate = coordinates[2].split(",").map { it.toInt() }

        if (firstCoordinate[0] == secondCoordinate[0] || firstCoordinate[1] == secondCoordinate[1]) {
            val lowX = if (firstCoordinate[0] < secondCoordinate[0]) firstCoordinate[0] else secondCoordinate[0]
            val highX = if (firstCoordinate[0] >= secondCoordinate[0]) firstCoordinate[0] else secondCoordinate[0]
            val lowY = if (firstCoordinate[1] < secondCoordinate[1]) firstCoordinate[1] else secondCoordinate[1]
            val highY = if (firstCoordinate[1] >= secondCoordinate[1]) firstCoordinate[1] else secondCoordinate[1]
            for (x in lowX..highX) {
                for (y in lowY..highY) {
                    grid.putIfAbsent(Pair(x, y), 0)
                    grid[Pair(x, y)] = (grid[Pair(x, y)] as Int).plus(1)
                }
            }
        }
    }

    return grid.count { it.value > 1 }
}

fun puzzle5dot1(): Int {
    val grid = mutableMapOf<Pair<Int, Int>, Int>()
    lines.forEach {
        val coordinates = it.split(" ")
        val firstCoordinate = coordinates[0].split(",").map { it.toInt() }
        val secondCoordinate = coordinates[2].split(",").map { it.toInt() }

        if (firstCoordinate[0] == secondCoordinate[0] || firstCoordinate[1] == secondCoordinate[1]) {
            val lowX = if (firstCoordinate[0] < secondCoordinate[0]) firstCoordinate[0] else secondCoordinate[0]
            val highX = if (firstCoordinate[0] >= secondCoordinate[0]) firstCoordinate[0] else secondCoordinate[0]
            val lowY = if (firstCoordinate[1] < secondCoordinate[1]) firstCoordinate[1] else secondCoordinate[1]
            val highY = if (firstCoordinate[1] >= secondCoordinate[1]) firstCoordinate[1] else secondCoordinate[1]
            for (x in lowX..highX) {
                for (y in lowY..highY) {
                    grid.putIfAbsent(Pair(x, y), 0)
                    grid[Pair(x, y)] = (grid[Pair(x, y)] as Int).plus(1)
                }
            }
        } else {
            val xIncline = (firstCoordinate[0] - secondCoordinate[0]).sign * -1
            val yIncline = (firstCoordinate[1] - secondCoordinate[1]).sign * -1

            val h = (firstCoordinate[0] - secondCoordinate[0]).absoluteValue

            for (i in 0..h) {
                val currentCoordinate = Pair(firstCoordinate[0] + i * xIncline, firstCoordinate[1] + i * yIncline)
                grid.putIfAbsent(currentCoordinate, 0)
                grid[currentCoordinate] = (grid[currentCoordinate] as Int).plus(1)
            }
        }
    }

    return grid.count { it.value > 1 }
}

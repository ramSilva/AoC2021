package puzzles

import java.io.File

private val lines = File("input/puzzle8/input.txt").readLines().toMutableList()

fun puzzle8(): Int {
    return lines.sumOf {
        it.split("|")[1].split(" ").filter { it.length in intArrayOf(2, 3, 4, 7) }.size
    }
}

fun puzzle8dot1(): Int {
    TODO()
}

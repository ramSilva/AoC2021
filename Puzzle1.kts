import java.io.File

val puzzle1 = File("input/puzzle1/input.txt").readLines().zipWithNext().count { it.first.toInt() < it.second.toInt() }
println(puzzle1)

fun solvePuzzle1dot1(): Int {
    return 0
}
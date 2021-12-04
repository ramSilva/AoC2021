package puzzles
import java.io.File

private val lines = File("input/puzzle1/input.txt").readLines()
fun puzzle1() = lines.zipWithNext().count { it.first.toInt() < it.second.toInt() }

fun puzzle1dot1() = lines.mapIndexed { index, s ->
    if (index + 2 >= lines.size) 0
    else s.toInt() + lines[index + 1].toInt() + lines[index + 2].toInt()
}.zipWithNext().count { it.first < it.second }


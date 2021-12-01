import java.io.File

val lines = File("input/puzzle1/input.txt").readLines()

val puzzle1 = lines.zipWithNext().count { it.first.toInt() < it.second.toInt() }
println(puzzle1)

val puzzle1dot1 = lines.mapIndexed { index, s ->
    if(index + 2 >= lines.size) 0
    else s.toInt() + lines[index+1].toInt() + lines[index+2].toInt()
}.zipWithNext().count { it.first.toInt() < it.second.toInt() }
println(puzzle1dot1)
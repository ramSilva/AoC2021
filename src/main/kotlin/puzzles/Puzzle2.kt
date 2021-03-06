package puzzles

import java.io.File

private val lines = File("input/puzzle2/input.txt").readLines()
//val lines = File("input/puzzle2/testInput.txt").readLines()

//2.0
fun puzzle2(): Int {
    var d = 0
    var h = 0
    lines.forEach {
        val operation = it.split(" ")[0]
        val ammount = it.split(" ")[1]

        when (operation) {
            "down" -> d += ammount.toInt()
            "up" -> d -= ammount.toInt()
            else -> h += ammount.toInt()
        }
    }
    return d * h
}

//2.1
fun puzzle2dot1(): Int {
    var d = 0
    var h = 0
    var aim = 0
    lines.forEach {
        val operation = it.split(" ")[0]
        val ammount = it.split(" ")[1]

        when (operation) {
            "down" -> aim += ammount.toInt()
            "up" -> aim -= ammount.toInt()
            else -> {
                h += ammount.toInt()
                d += ammount.toInt() * aim
            }

        }
    }
    return d * h
}

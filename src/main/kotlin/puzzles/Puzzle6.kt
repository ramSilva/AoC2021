package puzzles

import java.io.File
import java.util.*

//private val lines = File("input/puzzle6/input.txt").readLines()
private val lines = File("input/puzzle6/testInput.txt").readLines().toMutableList()

fun puzzle6(): Int {
    for (i in 0 until 80) {
        lines[0] = lines[0].split(",").map { it.toInt() }.fold("") { acc, v ->
            if (v == 0) {
                acc.plus(",6,8")
            } else {
                acc.plus(",${(v - 1)}")
            }
        }.trimStart(',')
    }

    return lines[0].split(",").count()
}

fun puzzle6masBem(): Long {
    val ages = lines[0].split(",").map { it.toInt() }.fold(LongArray(9)) { acc, v ->
        acc[v]++
        acc
    }.toMutableList()

    for (i in 0 until 256) {
        Collections.rotate(ages, -1)
        ages[6] += ages[8]
    }

    return ages.sum()
}

package puzzles

import java.io.File

//private val lines = File("input/puzzle6/input.txt").readLines()
private val lines = File("input/puzzle6/input.txt").readLines().toMutableList()

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
    var ages = lines[0].split(",").map { it.toInt() }.fold(mutableMapOf<Int, Long>()) { acc, v ->
        acc[v] = (acc[v] ?: 0).plus(1)
        acc
    }.toSortedMap()

    for (i in 0 until 256) {
        val newAges = ages.toSortedMap()
        var newFish = 0L

        ages.forEach { (k, v) ->
            if (k == 0) {
                newFish = newAges[k]!!
                newAges[0] = 0
            } else {
                newAges[k - 1] = (newAges[k - 1] ?: 0).plus(newAges[k]!!)
                newAges[k] = 0
            }
        }
        newAges[6] = (newAges[6] ?: 0).plus(newFish)
        newAges[8] = (newAges[8] ?: 0).plus(newFish)

        ages = newAges
    }

    var fish = 0L
    ages.forEach { (_, v) -> fish += v }
    return fish
}

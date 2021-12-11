package puzzles

import java.io.File

private val lines = File("input/puzzle11/input.txt").readLines().toMutableList()

private fun Pair<Int, Int>.adjacentPositions() = listOf(
    Pair(this.first - 1, this.second),
    Pair(this.first + 1, this.second),
    Pair(this.first, this.second - 1),
    Pair(this.first, this.second + 1),
    Pair(this.first - 1, this.second - 1),
    Pair(this.first + 1, this.second + 1),
    Pair(this.first + 1, this.second - 1),
    Pair(this.first - 1, this.second + 1)
)

fun puzzle11(): Int {
    val octopuses = mutableMapOf<Pair<Int, Int>, Int>()

    lines.forEachIndexed { iy, l ->
        l.forEachIndexed { ix, c ->
            octopuses[Pair(ix, iy)] = c.digitToInt()
        }
    }

    var flashed = 0

    for (i in 0 until 100) {
        var flashing = mutableSetOf<Pair<Int, Int>>()

        octopuses.forEach { (k, v) ->
            octopuses[k] = octopuses[k]!! + 1
        }

        while (true) {
            val flashingCopy = flashing.toMutableSet()

            octopuses.forEach { (k, _) ->

                if (k in flashing) return@forEach

                if (octopuses[k]!! > 9) {
                    flashingCopy.add(k)
                    k.adjacentPositions().forEach {
                        if (octopuses[it] != null) octopuses[it] = octopuses[it]!! + 1
                    }
                }
            }

            if (flashingCopy.size == flashing.size) {
                flashing = flashingCopy.toMutableSet()

                break
            }

            flashing = flashingCopy.toMutableSet()
        }
        flashing.forEach {
            flashed++
            octopuses[it] = 0
        }
    }

    return flashed
}

fun puzzle11dot1(): Int {
    val octopuses = mutableMapOf<Pair<Int, Int>, Int>()

    lines.forEachIndexed { iy, l ->
        l.forEachIndexed { ix, c ->
            octopuses[Pair(ix, iy)] = c.digitToInt()
        }
    }

    var i = 0
    while (true) {
        var flashing = mutableSetOf<Pair<Int, Int>>()

        octopuses.forEach { (k, v) ->
            octopuses[k] = octopuses[k]!! + 1
        }

        while (true) {
            val flashingCopy = flashing.toMutableSet()

            octopuses.forEach { (k, _) ->

                if (k in flashing) return@forEach

                if (octopuses[k]!! > 9) {
                    flashingCopy.add(k)
                    k.adjacentPositions().forEach {
                        if (octopuses[it] != null) octopuses[it] = octopuses[it]!! + 1
                    }
                }
            }

            if (flashingCopy.size == flashing.size) {
                flashing = flashingCopy.toMutableSet()

                break
            }

            flashing = flashingCopy.toMutableSet()
        }

        flashing.forEach {
            octopuses[it] = 0
        }

        i++

        if(flashing.size == octopuses.size) break
    }

    return i
}

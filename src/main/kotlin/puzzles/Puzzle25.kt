package puzzles

import java.io.File

private val lines = File("input/puzzle25/input.txt").readLines().toMutableList()
val height = lines.size
val width = lines[0].length

private fun Pair<Int, Int>.adjacentRight() =
    if (this.first == width - 1) Pair(0, this.second) else Pair(this.first + 1, this.second)

private fun Pair<Int, Int>.adjacentDown() =
    if (this.second == height - 1) Pair(this.first, 0) else Pair(this.first, this.second + 1)


fun puzzle25(): Int {
    var map = mutableMapOf<Pair<Int, Int>, Char>()

    lines.forEachIndexed { iy, l ->
        l.forEachIndexed { ix, c ->
            if (c != '.') map[Pair(ix, iy)] = c
        }
    }

    var i = 0
    while (true) {
        i++

        val newMap = map.toMutableMap()
        var moved = false

        map.forEach { (k, v) ->
            if (v == '>') {
                if (!map.containsKey(k.adjacentRight())) {
                    moved = true
                    newMap.remove(k)
                    newMap[k.adjacentRight()] = v
                }
            }
        }

        map = newMap.toMutableMap()

        map.forEach { (k, v) ->
            if (v == 'v') {
                if (!map.containsKey(k.adjacentDown())) {
                    moved = true
                    newMap.remove(k)
                    newMap[k.adjacentDown()] = v
                }
            }
        }

        map = newMap

        if (!moved) break
    }

    return i
}

fun puzzle25dot1(): Int {
    return 0
}
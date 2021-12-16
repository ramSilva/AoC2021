package puzzles

import java.io.File

private val lines = File("input/puzzle15/input.txt").readLines().toMutableList()

private fun Pair<Int, Int>.adjacentPositions() = listOf(
    Pair(this.first - 1, this.second),
    Pair(this.first + 1, this.second),
    Pair(this.first, this.second - 1),
    Pair(this.first, this.second + 1)
)

fun puzzle15(): Int {
    val map = mutableMapOf<Pair<Int, Int>, Int>()
    val unvisited = mutableListOf<Pair<Int, Int>>()
    val costs = mutableMapOf<Pair<Int, Int>, Int>()

    val height = lines.size
    val width = lines[0].length

    lines.forEachIndexed { iy, l ->
        l.forEachIndexed { ix, c ->
            map[Pair(ix, iy)] = c.digitToInt()
            unvisited.add(Pair(ix, iy))
            costs[Pair(ix, iy)] = Int.MAX_VALUE

        }
    }

    fun recursionBaby(currentCave: Pair<Int, Int>) {
        if (currentCave == Pair(width - 1, height - 1)) return

        currentCave.adjacentPositions().forEach { connection ->
            if (connection !in unvisited) return@forEach

            if (costs[connection]!! > costs[currentCave]!! + map[connection]!!) {
                costs[connection] = map[connection]!! + costs[currentCave]!!
            }
        }

        unvisited.remove(currentCave)

        val nextCave = unvisited.minByOrNull { costs[it]!! }!!

        recursionBaby(nextCave)
    }

    val initialCave = Pair(0, 0)

    unvisited.remove(initialCave)
    costs[initialCave] = 0
    recursionBaby(initialCave)

    return costs[Pair(width - 1, height - 1)]!!
}

fun puzzle15dot1(): Int {
    val map = mutableMapOf<Pair<Int, Int>, Int>()
    val unvisited = mutableSetOf<Pair<Int, Int>>()
    val visitedCosts = mutableMapOf<Pair<Int, Int>, Int>()
    val unvisitedCosts = mutableMapOf<Pair<Int, Int>, Int>()

    var height = lines.size
    var width = lines[0].length

    lines.forEachIndexed { iy, l ->
        l.forEachIndexed { ix, c ->
            for (x in 0 until 5) {
                for (y in 0 until 5) {
                    val points = mapOf(
                        Pair(ix + x * width, iy) to (c.digitToInt() + x - 1) % 9 + 1,
                        Pair(ix, iy + y * height) to (c.digitToInt() + y - 1) % 9 + 1,
                        Pair(ix + x * width, iy + y * height) to (c.digitToInt() + x + y - 1) % 9 + 1
                    )
                    points.forEach { point ->
                        map[point.key] = point.value
                        unvisited.add(point.key)
                    }
                }
            }
        }
    }

    height *= 5
    width *= 5

    fun dijkstra() {
        var currentCave = Pair(0, 0)

        unvisitedCosts[currentCave] = 0

        while (true) {
            currentCave.adjacentPositions().forEach { connection ->
                if (connection !in unvisited) return@forEach

                val connectionCost = unvisitedCosts[connection] ?: Int.MAX_VALUE

                if (connectionCost > unvisitedCosts[currentCave]!! + map[connection]!!) {
                    unvisitedCosts[connection] = unvisitedCosts[currentCave]!! + map[connection]!!
                }
            }

            visitedCosts[currentCave] = unvisitedCosts[currentCave]!!
            unvisitedCosts.remove(currentCave)

            unvisited.remove(currentCave)

            if (currentCave == Pair(width - 1, height - 1)) return

            currentCave = unvisitedCosts.minByOrNull { it.value }!!.key
        }
    }

    dijkstra()

    return visitedCosts[Pair(width - 1, height - 1)]!!
}
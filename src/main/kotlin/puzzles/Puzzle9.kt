package puzzles

import java.io.File

private val lines = File("input/puzzle9/input.txt").readLines().toMutableList()

fun List<List<Int>>.getValue(x: Int, y: Int) =
    ((this.getOrNull(y)) ?: emptyList()).getOrNull(x) ?: Int.MAX_VALUE

fun Pair<Int, Int>.adjacentPositions() = listOf(
    Pair(this.first - 1, this.second),
    Pair(this.first + 1, this.second),
    Pair(this.first, this.second - 1),
    Pair(this.first, this.second + 1)
)

fun isLowestPoint(map: List<List<Int>>, position: Pair<Int, Int>) =
    position.adjacentPositions().all { adjacentPosition ->
        map.getValue(position.first, position.second) < map.getValue(adjacentPosition.first, adjacentPosition.second)
    }

fun puzzle9(): Int {
    val map = mutableListOf<MutableList<Int>>()

    lines.forEach {
        val row = mutableListOf<Int>()

        it.forEach {
            row.add(it.toString().toInt())
        }

        map.add(row)
    }

    return map.foldIndexed(mutableListOf<Int>()) { y, acc, row ->
        row.forEachIndexed { x, number ->
            if (isLowestPoint(map, Pair(x, y))) {
                acc.add(number)
            }
        }
        acc
    }.sumOf { it + 1 }

}

fun puzzle9dot1(): Int {
    val map = mutableListOf<MutableList<Int>>()

    fun growBasin(
        currentBasin: List<Pair<Int, Int>>,
        checkedPos: List<Pair<Int, Int>>
    ): List<Pair<Int, Int>> {

        // fUnCtIoNaL PrOgRAmMiNg
        val newBasin = currentBasin.toMutableList()
        val checkedPosCopy = checkedPos.toMutableList()

        currentBasin.forEach { basinPosition ->
            basinPosition.adjacentPositions().forEach { adjacentPosition ->
                if (map.getValue(adjacentPosition.first, adjacentPosition.second) < 9
                    && !checkedPosCopy.contains(adjacentPosition)
                ) {
                    newBasin.add(adjacentPosition)
                    checkedPosCopy.add(adjacentPosition)
                }
            }
        }

        return if (newBasin.size == currentBasin.size) currentBasin // if there were no new positions added, the basin is complete
        else growBasin(newBasin, checkedPosCopy) // recursion baby
    }

    // create the map
    lines.forEach {
        val row = mutableListOf<Int>()

        it.forEach {
            row.add(it.toString().toInt())
        }

        map.add(row)
    }

    val basins = mutableListOf<List<Pair<Int, Int>>>()

    map.forEachIndexed { y, row ->
        row.forEachIndexed { x, _ ->
            if (isLowestPoint(map, Pair(x, y))) {
                val basin = growBasin(mutableListOf(Pair(x, y)), mutableListOf(Pair(x, y)))

                basins.add(basin)
            }
        }
    }

    return basins.sortedBy { it.size }.takeLast(3).map { it.size }.reduce { acc, size -> acc * size }
}

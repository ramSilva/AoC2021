package puzzles

import java.io.File

private val lines = File("input/puzzle9/input.txt").readLines().toMutableList()

fun MutableList<MutableList<Int>>.getValue(x: Int, y: Int) =
    ((this.getOrNull(y)) ?: emptyList()).getOrNull(x) ?: Int.MAX_VALUE

fun puzzle9(): Int {
    val map = mutableListOf<MutableList<Int>>()

    lines.forEach {
        val row = mutableListOf<Int>()

        it.forEach {
            row.add(it.toString().toInt())
        }

        map.add(row)
    }

    return map.foldIndexed(mutableListOf<Int>()) { iy, acc, row ->
        row.forEachIndexed { ix, number ->
            val ys = intArrayOf(iy - 1, iy, iy + 1)
            val xs = intArrayOf(ix - 1, ix, ix + 1)
            if (ys.all { y ->
                    xs.all { x ->
                        number < map.getValue(x, y) || (y == iy && x == ix)
                    }
                }) acc.add(number)
        }
        acc
    }.sumOf { it + 1 }

}

fun puzzle9dot1(): Int {
    val map = mutableListOf<MutableList<Int>>()

    fun isLowestPoint(pos: Pair<Int, Int>): Boolean {
        val (posX, posY) = pos
        val ys = intArrayOf(posY - 1, posY, posY + 1)
        val xs = intArrayOf(posX - 1, posX, posX + 1)

        return (ys.all { y ->
            xs.all { x ->
                map.getValue(posX, posY) < map.getValue(x, y) || (y == posY && x == posX)
            }
        })
    }

    fun growBasin(
        pos: List<Pair<Int, Int>>,
        checkedPos: List<Pair<Int, Int>>
    ): List<Pair<Int, Int>> {

        // fUnCtIoNaL PrOgRAmMiNg
        val newBasin = pos.toMutableList()
        val checkPosCopy = checkedPos.toMutableList()

        pos.forEach { p ->
            val (posX, posY) = p

            // get the 4 adjacent coordinates
            val posToCheck = listOf(
                Pair(posX - 1, posY),
                Pair(posX + 1, posY),
                Pair(posX, posY - 1),
                Pair(posX, posY + 1)
            )

            posToCheck.forEach {
                val (x, y) = it

                val newPos = Pair(x, y)
                if (map.getValue(x, y) < 9 && !checkPosCopy.contains(newPos)) {
                    newBasin.add(newPos)
                    checkPosCopy.add(newPos)
                }
            }
        }

        return if (newBasin.size == pos.size) pos // if there were no new positions added, the basin is complete
        else growBasin(newBasin, checkPosCopy) // recursion baby
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
            if (isLowestPoint(Pair(x, y))) {
                val basin = growBasin(mutableListOf(Pair(x, y)), mutableListOf(Pair(x, y)))

                basins.add(basin)
            }
        }
    }

    return basins.sortedBy { it.size }.takeLast(3).map { it.size }.reduce { acc, size -> acc * size }
}

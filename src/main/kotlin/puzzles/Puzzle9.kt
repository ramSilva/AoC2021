package puzzles

import java.io.File
import kotlin.math.pow

private val lines = File("input/puzzle9/input.txt").readLines().toMutableList()

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
                        number < ((((map.getOrNull(y)) ?: emptyList()).getOrNull(x)) ?: Int.MAX_VALUE) || (y == iy && x == ix)
                    }
                }) acc.add(number)
        }
        acc
    }.sumOf { it + 1 }

}

fun puzzle9dot1(): Int {
    TODO()
}

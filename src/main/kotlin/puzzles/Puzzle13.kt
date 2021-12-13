package puzzles

import java.io.File
import kotlin.math.absoluteValue

private val lines = File("input/puzzle13/input.txt").readLines().toMutableList()

// My first part is dumb. Hardcoding the fold logic does nothing to help solve the 2nd half
fun puzzle13(): Int {
    val (dots, folds) = lines.partition { it.isEmpty() || it[0].isDigit() }
    return dots.filter { it.isNotEmpty() }.fold(mutableSetOf<Pair<Int, Int>>()) { acc, dot ->
        val (x, y) = dot.split(",").map { it.toInt() }

        acc.add(Pair((folds[0].substringAfter('=').toInt() - x).absoluteValue, y))

        acc
    }.size
}

fun puzzle13dot1(): Int {
    val (dots, folds) = lines.partition { it.isEmpty() || it[0].isDigit() }
    val (xfolds, yfolds) = folds.partition { it.contains('x') }

    val paper = mutableSetOf<Pair<Int, Int>>()
    dots.filterNot { it.isEmpty() }.fold(paper) { acc, dot ->
        val (x, y) = dot.split(",").map { it.toInt() }

        val finalx = xfolds.map { it.substringAfter('=').toInt() }.fold(x) { acc, fold ->
            if (acc > fold) fold - (acc - fold)
            else acc
        }

        val finaly = yfolds.map { it.substringAfter('=').toInt() }.fold(y) { acc, fold ->
            if (acc > fold) fold - (acc - fold)
            else acc
        }

        acc.add(Pair(finalx, finaly))

        acc
    }

    for (y in 0..paper.maxByOrNull { it.second }!!.second) {
        var line = ""
        for (x in 0..paper.maxByOrNull { it.first }!!.first) {
            line += if (Pair(x, y) in paper) "█" else " "
        }
        println(line)
    }

    return 0
}

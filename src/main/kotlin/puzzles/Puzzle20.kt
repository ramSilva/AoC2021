package puzzles

import java.io.File
import kotlin.math.absoluteValue

private val lines = File("input/puzzle20/input.txt").readLines().toMutableList()

private fun Pair<Int, Int>.adjacentPositions() = listOf(
    Pair(this.first - 1, this.second - 1),
    Pair(this.first, this.second - 1),
    Pair(this.first + 1, this.second - 1),

    Pair(this.first - 1, this.second),
    Pair(this.first, this.second),
    Pair(this.first + 1, this.second),

    Pair(this.first - 1, this.second + 1),
    Pair(this.first, this.second + 1),
    Pair(this.first + 1, this.second + 1),
)

fun puzzle20(): Int {
    var image = mutableMapOf<Pair<Int, Int>, Int>()
    val algorithm = lines[0]
    var infinity = 0

    lines.subList(2, lines.size).forEachIndexed { iy, l ->
        l.forEachIndexed { ix, c ->
            image[Pair(ix, iy)] = if (c == '#') 1 else 0
        }
    }

    var xOfInterestMin = image.minOf { (k, _) -> k.first } - 2
    var xOfInterestMax = image.maxOf { (k, _) -> k.first } + 2
    var yOfInterestMin = image.minOf { (k, _) -> k.second } - 2
    var yOfInterestMax = image.maxOf { (k, _) -> k.second } + 2

    lines.subList(2, lines.size).forEachIndexed { iy, l ->
        l.forEachIndexed { ix, c ->
            image[Pair(ix, iy)] = if (c == '#') 1 else 0
        }
    }

    for (i in 0 until 50) {
        val newImage = image.toMutableMap()
        for (y in yOfInterestMin..yOfInterestMax) {
            for (x in xOfInterestMin..xOfInterestMax) {
                var bin = ""
                Pair(x, y).adjacentPositions().forEach {
                    bin += image[it] ?: infinity
                }
                newImage[Pair(x, y)] = if (algorithm[bin.toInt(2)] == '#') 1 else 0
            }
        }

        image = newImage.toMutableMap()

        xOfInterestMin = image.minOf { (k, _) -> k.first } - 2
        xOfInterestMax = image.maxOf { (k, _) -> k.first } + 2
        yOfInterestMin = image.minOf { (k, _) -> k.second } - 2
        yOfInterestMax = image.maxOf { (k, _) -> k.second } + 2

        if(algorithm[0] == '#') infinity = (infinity - 1).absoluteValue

    }

    return image.filter { (k, v) -> v == 1 }.count()
}

fun puzzle20dot1(): Long {
    return 0
}
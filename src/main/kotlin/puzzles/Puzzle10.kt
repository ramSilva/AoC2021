package puzzles

import java.io.File
import java.util.*

private val lines = File("input/puzzle10/input.txt").readLines().toMutableList()

fun puzzle10(): Int {
    val open = "(<{["
    val close = ")>}]"
    val scores = mapOf(Pair(')', 3), Pair(']', 57), Pair('}', 1197), Pair('>', 25137))

    return lines.sumOf { line ->
        val expectedStack = ArrayDeque<Char>()
        line.sumOf { char ->
            val openIndex = open.indexOf(char)
            if (openIndex >= 0) {
                expectedStack.push(close[openIndex])
            } else {
                val expected = expectedStack.pop()
                if (char != expected) {
                    return@sumOf scores[char]!!
                }
            }

            0
        }
    }
}

fun puzzle10dot1(): Long {
    val open = "(<{["
    val close = ")>}]"
    val scores = mapOf(Pair(')', 1), Pair(']', 2), Pair('}', 3), Pair('>', 4))

    return lines.map { line ->
        val expectedStack = ArrayDeque<Char>()
        line.forEach { char ->
            val openIndex = open.indexOf(char)
            if (openIndex >= 0) {
                expectedStack.push(close[openIndex])
            } else {
                val expected = expectedStack.pop()
                if (char != expected) {
                    expectedStack.clear()
                    return@map 0
                }
            }
        }

        expectedStack.fold(0L) { acc, c -> acc * 5 + scores[c]!! }
    }.filter { it != 0L }.sorted().let { it[it.size / 2] }
}

val open = "(<{["
val close = ")>}]"
val scores = mapOf(')' to 3, ']' to 57, '}' to 1197, '>' to 25137)
val scores2 = mapOf(')' to 1, ']' to 2, '}' to 3, '>' to 4)

fun puzzle10golf() = lines.map { line ->
    line.fold(ArrayDeque<Char>()) { acc, char ->
        if (char in open) acc.push(close[open.indexOf(char)])
        else if (char != acc.pop()) return@map scores[char]!!

        acc
    }.let { 0 }
}.sum()

fun puzzle10dot1golf() = lines.map { line ->
    line.fold(ArrayDeque<Char>()) { acc, char ->
        if (char in open) acc.push(close[open.indexOf(char)])
        else if (char != acc.pop()) return@map 0

        acc
    }.fold(0L) { acc, c -> acc * 5 + scores2[c]!! }
}.filter { it != 0L }.sorted().let { it[it.size / 2] }

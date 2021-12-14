package puzzles

import java.io.File
import kotlin.math.absoluteValue

private val lines = File("input/puzzle14/input.txt").readLines().toMutableList()

fun puzzle14(): Int {
    var polymer = lines[0]

    val rules = mutableMapOf<String, String>()
    lines.subList(2, lines.size).forEach { rule ->
        val (input, output) = rule.split(" -> ")
        rules[input] = output
    }

    for (step in 0 until 40) {
        println("""$step, ${polymer.length}""")
        var newPolymer = ""

        for (polymerIndex in 0 until polymer.lastIndex) {
            val (a, b) = Pair(polymer[polymerIndex], polymer[polymerIndex + 1])

            val newChar = rules[a.toString() + b]!!

            newPolymer += """$a$newChar"""
        }

        newPolymer += polymer.last()

        polymer = newPolymer
    }

    val mostCommon = polymer.maxByOrNull { c -> polymer.filter { it == c }.length }
    val leastCommon = polymer.minByOrNull { c -> polymer.filter { it == c }.length }

    return polymer.count { it == mostCommon } - polymer.count { it == leastCommon }
}

fun puzzle14dot1(): Long {
    val count = mutableMapOf<Char, Long>()
    var polymer = mutableMapOf<String, Long>()
    lines[0].zipWithNext { a, b ->
        count[a] = (count[a] ?: 0).plus(1)

        val pair = a.toString() + b
        polymer[pair] = (polymer[pair] ?: 0).plus(1)
    }
    count[lines[0].last()] = (count[lines[0].last()] ?: 0).plus(1)

    val rules = mutableMapOf<String, Char>()
    lines.subList(2, lines.size).forEach { rule ->
        val (input, output) = rule.split(" -> ")
        rules[input] = output.first()
    }


    for (step in 0 until 40) {
        val newPolymer = polymer.toMutableMap()

        polymer.forEach { (k, v) ->
            if (v == 0L) return@forEach

            val newLetter = rules[k]!!
            val pair1 = k.first() + newLetter.toString()
            val pair2 = newLetter.toString() + k.last()

            newPolymer[k] = newPolymer[k]!! - v
            newPolymer[pair1] = (newPolymer[pair1] ?: 0).plus(v)
            newPolymer[pair2] = (newPolymer[pair2] ?: 0).plus(v)

            count[newLetter] = (count[newLetter] ?: 0).plus(v)
        }

        polymer = newPolymer
    }


    return count.maxByOrNull { it.value }!!.value - count.minByOrNull { it.value }!!.value
}
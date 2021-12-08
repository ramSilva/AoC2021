package puzzles

import java.io.File
import java.util.*
import kotlin.math.absoluteValue
import kotlin.system.measureNanoTime
import kotlin.system.measureTimeMillis

private val lines = File("input/puzzle7/input.txt").readLines().toMutableList()

fun puzzle7(): Int {
    val values = lines[0].split(",").map { it.toInt() }

    return values.fold(mutableListOf<Int>()) { acc, v ->
        acc.add(values.sumOf { (it - v).absoluteValue })

        acc
    }.minOrNull()!!
}

fun puzzle7dot1(): Int {
    val values = lines[0].split(",").map { it.toInt() }

    return (values.minOrNull()!!..values.maxOrNull()!!)
        .fold(mutableListOf<Int>()) { acc, v ->
            acc.add(values.sumOf {
                val distance = (it - v).absoluteValue

                (distance * (distance + 1)) / 2 // triangle numbers
            })

            acc
        }.minOrNull()!!
}

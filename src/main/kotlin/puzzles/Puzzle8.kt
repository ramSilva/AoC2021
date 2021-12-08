package puzzles

import java.io.File
import kotlin.math.pow

private val lines = File("input/puzzle8/input.txt").readLines().toMutableList()

fun puzzle8(): Int {
    return lines.sumOf {
        it.split("|")[1].split(" ").filter { it.length in intArrayOf(2, 3, 4, 7) }.size
    }
}

fun puzzle8dot1(): Int {
    // Returns a string containing the characters that don't match both strings
    fun String.stringDiff(s2: String): String {
        var diff = ""
        this.forEach {
            if (!s2.contains(it)) diff += it
        }

        return diff
    }

    /**
     * Filters the candidates list by doing the following logic:
     *
     *      - in the selected indices, removes letters not on the input string
     *      - in the non selected indices, removes letters present on the input string
     */
    fun filterCandidates(
        candidates: MutableList<String>,
        input: String,
        vararg indices: Int,
        removeNegated: Boolean = true
    ) {
        val negatedIndices = MutableList(7) { it }.filter { !indices.contains(it) }

        indices.forEach {
            candidates[it].stringDiff(input).forEach { c ->
                candidates[it] = candidates[it].replace(c.toString(), "")
            }
        }

        if (removeNegated) {
            negatedIndices.forEach {
                input.forEach { c ->
                    candidates[it] = candidates[it].replace(c.toString(), "")
                }
            }
        }
    }

    return lines.sumOf {
        // For each input line, at the start each segment can be each wire
        val candidates = MutableList(7) { "abcdefg" }

        val (inputsArray, outputsArray) = it.split(" | ")

        val inputs = inputsArray.split(" ")

        // Reduce each segment candidate to at most 2 wires
        while (candidates.count { it.length > 2 } > 0) {
            inputs.forEach { input ->
                when (input.length) {
                    2 -> {
                        filterCandidates(candidates, input, 2, 5)
                    }
                    3 -> {
                        filterCandidates(candidates, input, 0, 2, 5)
                    }
                    4 -> {
                        filterCandidates(candidates, input, 1, 2, 3, 5)
                    }
                }
            }
        }

        // Given the limited candidates, each number can be uniquely identified
        outputsArray.split(" ").fold(mutableListOf<Int>()) { acc, output ->
            when (output.length) {
                2 -> acc.add(0, 1)
                3 -> acc.add(0, 7)
                4 -> acc.add(0, 4)
                5 -> {
                    if (candidates[2].all { output.contains(it) } && candidates[5].all { output.contains(it) }) {
                        acc.add(0, 3)
                    } else if (candidates[0].all { output.contains(it) } && candidates[1].all { output.contains(it) }) {
                        acc.add(0, 5)
                    } else {
                        acc.add(0, 2)
                    }
                }
                6 -> {
                    if (candidates[2].all { output.contains(it) } && candidates[4].all { output.contains(it) }) {
                        acc.add(0, 0)
                    }
                    if (candidates[3].all { output.contains(it) } && candidates[4].all { output.contains(it) }) {
                        acc.add(0, 6)
                    }
                    if (candidates[2].all { output.contains(it) } && candidates[3].all { output.contains(it) }) {
                        acc.add(0, 9)
                    }
                }
                7 -> acc.add(0, 8)
            }

            acc
        }.reduceIndexed { index, acc, number -> acc + number * 10.0f.pow(index).toInt() }.also { println(it) }
    }
}

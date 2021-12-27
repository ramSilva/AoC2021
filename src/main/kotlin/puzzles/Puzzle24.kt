package puzzles

import java.io.File

private val lines = File("input/puzzle24/input.txt").readLines().toMutableList()


val input = """
    z = z / 1*
    
    if (z mod 26) + 14* != w?
        z = z*26
        y = w + 8*
    else
        y = 0
    
    z = z + y
    
    ---------
    2
    4
    9
    1
    3
    1
    1
    1
    6
    1
    6
    1
    5
    1
""".trimIndent()

fun puzzle24(): Long {
    var guess = "24913111616151"
    while (true) {
        val registries = mutableMapOf("w" to 0, "z" to 0, "y" to 0, "x" to 0)
        var trimmedGuess = guess
        lines.forEach { l ->
            val (operation, rest) = """(.*?) (.*)""".toRegex().find(l)!!.destructured
            when (operation) {
                "inp" -> {
                    registries[rest] = trimmedGuess[0].digitToInt()
                    trimmedGuess = trimmedGuess.substring(1)
                }
                "mul" -> {
                    val (op1, op2) = """(.*) (.*)""".toRegex().find(rest)!!.destructured
                    if (op2.toIntOrNull() != null) {
                        registries[op1] = registries[op1]!! * op2.toInt()
                    } else {
                        registries[op1] = registries[op1]!! * registries[op2]!!
                    }
                }
                "add" -> {
                    val (op1, op2) = """(.*) (.*)""".toRegex().find(rest)!!.destructured
                    if (op2.toIntOrNull() != null) {
                        registries[op1] = registries[op1]!! + op2.toInt()
                    } else {
                        registries[op1] = registries[op1]!! + registries[op2]!!
                    }
                }
                "div" -> {
                    val (op1, op2) = """(.*) (.*)""".toRegex().find(rest)!!.destructured
                    if (op2.toIntOrNull() != null) {
                        registries[op1] = registries[op1]!! / op2.toInt()
                    } else {
                        registries[op1] = registries[op1]!! / registries[op2]!!
                    }
                }
                "mod" -> {
                    val (op1, op2) = """(.*) (.*)""".toRegex().find(rest)!!.destructured
                    if (op2.toIntOrNull() != null) {
                        registries[op1] = registries[op1]!! % op2.toInt()
                    } else {
                        registries[op1] = registries[op1]!! % registries[op2]!!
                    }
                }
                "eql" -> {
                    val (op1, op2) = """(.*) (.*)""".toRegex().find(rest)!!.destructured
                    if (op2.toIntOrNull() != null) {
                        registries[op1] = if (registries[op1]!! == op2.toInt()) 1 else 0
                    } else {
                        registries[op1] = if (registries[op1]!! == registries[op2]!!) 1 else 0
                    }
                }
            }

        }

        println("""$guess, ${registries["z"]!!}""")
        if (registries["z"]!! == 0) break
        while (true) {
            guess = (guess.toLong() - 1).toString()
            if (!guess.contains('0')) break
        }
    }

    return guess.toLong()
}

fun puzzle24dot1(): Int {
    return 0
}
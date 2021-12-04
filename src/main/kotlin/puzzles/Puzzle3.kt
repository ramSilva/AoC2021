package puzzles

import java.io.File

private val lines = File("input/puzzle3/input.txt").readLines()
//val lines = File("input/puzzle3/testInput.txt").readLines()

fun puzzle3(): Int {
    val oneBits = IntArray(lines[0].length) // amount of 1 bits per position

    lines.forEach { s ->
        s.forEachIndexed { index, c ->
            if (c == '1') oneBits[index]++
        }
    }

    val gamma = oneBits.fold("") { a, v ->
        if (v > lines.size / 2) a + "1"
        else a + "0"
    }

    val epsilon = gamma.map {
        if(it == '1') '0'
        else '1'
    }.joinToString("")

    println(gamma)
    println(epsilon)

    return gamma.toInt(2)*epsilon.toInt(2)
}

fun puzzle3dot1(): Int {
    fun trimO2(input: List<String>, index: Int): List<String>{
        var oneBit = 0

        input.forEach { s ->
            if (s[index] == '1') oneBit++
        }

        val filter = if (oneBit >= input.size / 2.0f) '1' else '0'

        val result =  input.filter { it[index] == filter }
        return result
    }

    fun trimCO2(input: List<String>, index: Int): List<String>{
        var oneBit = 0

        input.forEach { s ->
            if (s[index] == '1') oneBit++
        }

        val filter = if (oneBit >= input.size / 2.0f) '0' else '1'

        val result =  input.filter { it[index] == filter }
        return result
    }

    var potentialO2 = lines
    var i = 0
    while(potentialO2.size > 1){
        potentialO2 = trimO2(potentialO2, i)
        i++
    }

    var potentialCO2 = lines
    var j = 0
    while(potentialCO2.size > 1){
        potentialCO2 = trimCO2(potentialCO2, j)
        j++
    }

    println(potentialO2)
    println(potentialCO2)

    return potentialO2[0].toInt(2) * potentialCO2[0].toInt(2)
}

import java.io.File

val lines = File("input/puzzle3/input.txt").readLines()
//val lines = File("input/puzzle3/testInput.txt").readLines()

fun puzzle3() {
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

    println(gamma.toInt(2)*epsilon.toInt(2))
}

puzzle3()
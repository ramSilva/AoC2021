package puzzles

import java.io.File
import kotlin.math.max

private val lines = File("input/puzzle17/input.txt").readLines().toMutableList()

fun puzzle17(): Int {
    val (minX, maxX, minY, maxY) = """.*x=(-?\d*)\.\.(-?\d*).*y=(-?\d*)\.\.(-?\d*)""".toRegex()
        .find(lines[0])!!.destructured.toList().map { it.toInt() }

    var tentativeYVelocity = 0
    var highest = 0

    // You'll notice this while never ends, so one just needs to watch the prints and see when new ones stop appearing
    while (true) {
        var yPosition = 0
        var yVelocity = tentativeYVelocity
        var tentativeHighest = 0

        while (true) {
            yPosition += yVelocity
            tentativeHighest = max(yPosition, tentativeHighest)

            if (yPosition in minY..maxY) {
                highest = max(tentativeHighest, highest)
                println("""$highest, $tentativeYVelocity""")
                break
            }
            if (yPosition < minY) break
            yVelocity--
        }


        tentativeYVelocity++
    }

    return highest
}

fun puzzle17dot1(): Int {
    val (minX, maxX, minY, maxY) = """.*x=(-?\d*)\.\.(-?\d*).*y=(-?\d*)\.\.(-?\d*)""".toRegex()
        .find(lines[0])!!.destructured.toList().map { it.toInt() }

    val validVelocities = mutableListOf<Pair<Int, Int>>()

    (-1000..1000).forEach { tentativeXVelocity ->
        (-1000..1000).forEach { tentativeYVelocity ->

            var xVelocity = tentativeXVelocity
            var yVelocity = tentativeYVelocity
            var xPosition = 0
            var yPosition = 0

            while (true){
                xPosition += xVelocity
                yPosition += yVelocity

                if(xPosition in minX..maxX && yPosition in minY..maxY){
                    validVelocities.add(Pair(tentativeXVelocity, tentativeYVelocity))
                    break
                }
                if(xPosition > maxX || yPosition < minY) break

                yVelocity--
                if(xVelocity > 0) xVelocity--
            }
        }
    }

    return validVelocities.size
}
package puzzles

import java.io.File
import kotlin.math.max

private val lines = File("input/puzzle22/input.txt").readLines().toMutableList()


data class Pos(
    val x: Int,
    val y: Int,
    val z: Int
)

fun puzzle22(): Int {

    val cubes = mutableSetOf<Pos>()

    lines.forEach {
        val (startX, endX) = """.*x=(-?\d*)\.\.(-?\d*)""".toRegex().find(it)!!.destructured.toList()
            .map { it.toInt() }
        val (startY, endY) = """.*y=(-?\d*)\.\.(-?\d*)""".toRegex().find(it)!!.destructured.toList()
            .map { it.toInt() }
        val (startZ, endZ) = """.*z=(-?\d*)\.\.(-?\d*)""".toRegex().find(it)!!.destructured.toList()
            .map { it.toInt() }
        val turnOn = it[1] == 'n'

        for (x in startX..endX) {
            if (x > 50 || x < -50) break
            for (y in startY..endY) {
                if (y > 50 || y < -50) break
                for (z in startZ..endZ) {
                    if (z > 50 || z < -50) break
                    if (turnOn) cubes.add(Pos(x, y, z))
                    else cubes.remove(Pos(x, y, z))
                }
            }
        }
    }

    return cubes.size
}

fun puzzle22dot1(): Int {
    val cubes = mutableSetOf<Pos>()

    lines.forEach {
        val (startX, endX) = """.*x=(-?\d*)\.\.(-?\d*)""".toRegex().find(it)!!.destructured.toList()
            .map { it.toInt() }
        val (startY, endY) = """.*y=(-?\d*)\.\.(-?\d*)""".toRegex().find(it)!!.destructured.toList()
            .map { it.toInt() }
        val (startZ, endZ) = """.*z=(-?\d*)\.\.(-?\d*)""".toRegex().find(it)!!.destructured.toList()
            .map { it.toInt() }
        val turnOn = it[1] == 'n'

        for (x in startX..endX) {
            for (y in startY..endY) {
                for (z in startZ..endZ) {
                    if (turnOn) cubes.add(Pos(x, y, z))
                    else cubes.remove(Pos(x, y, z))
                }
            }
        }
    }

    return cubes.size
}
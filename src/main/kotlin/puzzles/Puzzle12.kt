package puzzles

import java.io.File

private val lines = File("input/puzzle12/input.txt").readLines().toMutableList()

fun puzzle12(): Int {
    val connections = mutableMapOf<String, MutableList<String>>()

    lines.forEach { l ->
        val (cave1, cave2) = l.split("-")

        connections.putIfAbsent(cave1, mutableListOf())
        connections[cave1]!!.add(cave2)

        connections.putIfAbsent(cave2, mutableListOf())
        connections[cave2]!!.add(cave1)
    }

    fun recursionBaby(currentCave: String, pathSoFar: List<String>): List<List<String>> {
        val possiblePaths = mutableListOf<MutableList<String>>()
        val result = mutableListOf<List<String>>()

        if (currentCave == "end") return listOf(pathSoFar)

        connections[currentCave]!!.forEach { connection ->
            if (connection == connection.lowercase() && connection in pathSoFar) return@forEach

            val newPath = pathSoFar.toMutableList()
            newPath.add(connection)

            possiblePaths.add(newPath)

            recursionBaby(connection, newPath).forEach { path ->
                result.add(path)
            }
        }

        return result
    }

    return recursionBaby("start", mutableListOf("start")).size
}

fun puzzle12dot1(): Int {
    val connections = mutableMapOf<String, MutableList<String>>()

    lines.forEach { l ->
        val (cave1, cave2) = l.split("-")

        connections.putIfAbsent(cave1, mutableListOf())
        connections[cave1]!!.add(cave2)

        connections.putIfAbsent(cave2, mutableListOf())
        connections[cave2]!!.add(cave1)
    }

    fun recursionBaby(currentCave: String, pathSoFar: List<String>, visitedSmall: Boolean): List<List<String>> {
        val possiblePaths = mutableListOf<MutableList<String>>()
        val result = mutableListOf<List<String>>()


        if (currentCave == "end") return listOf(pathSoFar)

        connections[currentCave]!!.forEach { connection ->
            var willVisitSmall = visitedSmall
            if (connection == "start") return@forEach

            if (connection == connection.lowercase() && connection in pathSoFar) {
                if (visitedSmall) return@forEach
                else willVisitSmall = true
            }

            val newPath = pathSoFar.toMutableList()
            newPath.add(connection)

            possiblePaths.add(newPath)

            recursionBaby(connection, newPath, willVisitSmall).forEach { path ->
                result.add(path)
            }
        }

        return result
    }

    return recursionBaby("start", mutableListOf("start"), false).size
}

package puzzles

fun puzzle21(): Int {

    var p1 = 9
    var p2 = 10
    var d = 0
    var rolls = 0
    var p1Score = 0
    var p2Score = 0
    var p1turn = true

    while (true) {
        var sum = 0
        for (r in 1..3) {
            d = (d % 100) + 1
            sum += d
            rolls++
        }

        if (p1turn) {
            p1 = (p1 + sum - 1) % 10 + 1
            p1Score += p1
        } else {
            p2 = (p2 + sum - 1) % 10 + 1
            p2Score += p2
        }

        if (p1Score >= 1000) return rolls * p1Score
        else if (p2Score >= 1000) return rolls * p1Score

        p1turn = !p1turn
    }
}

//doesnt work
fun puzzle21dot1(): Long {
    data class Info(
        val p1turn: Boolean,
        val roll: Int,
        val sum: Int,
        val p1Pos: Int,
        val p1Score: Int,
        val p2Pos: Int,
        val p2Score: Int
    )

    val wins = mutableListOf(0L, 0L)
    val p1cache = mutableMapOf<Info, Int>()
    val p2cache = mutableMapOf<Info, Int>()
    val p1cacheHit = mutableMapOf<Info, Int>()
    val p2cacheHit = mutableMapOf<Info, Int>()

    fun roll(info: Info, previousInfos: List<Info>) {
        val p1turn = info.p1turn
        val roll = info.roll
        val sum = info.sum
        var p1Pos = info.p1Pos
        var p2Pos = info.p2Pos
        var p1Score = info.p1Score
        var p2Score = info.p2Score

        val newInfos = mutableListOf<Info>()

        if (roll == 3) {
            if (p1turn) {
                p1Pos = (p1Pos + sum - 1) % 10 + 1
                p1Score += p1Pos

            } else {
                p2Pos = (p2Pos + sum - 1) % 10 + 1
                p2Score += p2Pos

            }
            val newInfo = Info(
                !p1turn,
                0,
                0,
                p1Pos,
                p1Score,
                p2Pos,
                p2Score
            )

            if (p1Score >= 21) {
                previousInfos.plus(newInfo).forEach { previousInfo ->
                    p1cache.putIfAbsent(previousInfo, 0)
                    p1cache[previousInfo] = p1cache[previousInfo]!! + 1
                }
                wins[0]++
                return
            }
            if (p2Score >= 21) {
                previousInfos.plus(newInfo).forEach { previousInfo ->
                    p2cache.putIfAbsent(previousInfo, 0)
                    p2cache[previousInfo] = p2cache[previousInfo]!! + 1
                }
                wins[1]++
                return
            }
            newInfos.add(newInfo)
        } else {
            listOf(sum + 1, sum + 2, sum + 3).forEach { newSum ->
                newInfos.add(
                    Info(
                        p1turn,
                        roll + 1,
                        newSum,
                        p1Pos,
                        p1Score,
                        p2Pos,
                        p2Score
                    )
                )
            }
        }

        newInfos.forEach { newInfo ->
            if (p1cache.containsKey(newInfo)) {
                previousInfos.plus(newInfo).forEach { previousInfo ->
                    p1cache.putIfAbsent(previousInfo, 0)
                    p1cache[previousInfo] = p1cache[previousInfo]!! + 1
                    p1cacheHit.putIfAbsent(previousInfo, 0)
                    p1cacheHit[previousInfo] = p1cacheHit[previousInfo]!! + 1
                }
                return@forEach
            }
            if (p2cache.containsKey(newInfo)) {
                previousInfos.plus(newInfo).forEach { previousInfo ->
                    p2cache.putIfAbsent(previousInfo, 0)
                    p2cache[previousInfo] = p2cache[previousInfo]!! + 1
                    p2cacheHit.putIfAbsent(previousInfo, 0)
                    p2cacheHit[previousInfo] = p2cacheHit[previousInfo]!! + 1
                }
                return@forEach
            }

            val extraInfo = previousInfos.toMutableList()
            extraInfo.add(newInfo)
            roll(newInfo, extraInfo)
        }
    }

    val initialInfo = Info(true, 0, 0, 4, 0, 8, 0)

    roll(initialInfo, listOf(initialInfo))

    p1cacheHit.forEach { (k, v) ->
        wins[0] = wins[0] + p1cache[k]!! * v
    }
    p2cacheHit.forEach { (k, v) ->
        wins[1] = wins[1] + p2cache[k]!! * v
    }

    return wins.maxOrNull()!!
}
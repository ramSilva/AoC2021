import java.io.File

val lines = File("input/puzzle4/input.txt").readLines()
//val lines = File("input/puzzle4/testInput.txt").readLines()

fun puzzle4() {
    data class Entry(
        val number: Int,
        val checked: Boolean
    )

    val bingo = lines[0]
    var boards = mutableListOf<MutableList<Entry>>()

    for (i in 2..lines.size step 6) {
        val board = mutableListOf<Entry>()
        for (j in 0..4) {
            val line = lines[i + j]
            line.trim().split("\\s+".toRegex()).forEach {
                board.add(Entry(it.toInt(), false))
            }
        }
        boards.add(board)
    }

    bingo.split(",")
        .map {
            it.toInt()
        }
        .forEach { number ->
            boards = boards.map {
                val newBoard = it.map {
                    if (it.number == number) Entry(it.number, true)
                    else it
                }.toMutableList()

                for (x in 0..24 step 5) {
                    if (newBoard[x].checked
                        && newBoard[x + 1].checked
                        && newBoard[x + 2].checked
                        && newBoard[x + 3].checked
                        && newBoard[x + 4].checked) {
                        val uncheckedSum = newBoard.filter { !it.checked }.sumOf { it.number }
                        println(uncheckedSum * number)
                        return
                    }
                }
                for (y in 0..4) {
                    if (newBoard[y].checked
                        && newBoard[y + 5].checked
                        && newBoard[y + 10].checked
                        && newBoard[y + 15].checked
                        && newBoard[y + 20].checked) {
                        val uncheckedSum = newBoard.filter { !it.checked }.sumOf { it.number }
                        println(uncheckedSum * number)
                        return
                    }
                }

                newBoard
            }.toMutableList()
        }
}

fun puzzle4dot1() {
    data class Entry(
        val number: Int,
        val checked: Boolean
    )

    fun checkWin(board: List<Entry>): Boolean {
        for (x in 0..24 step 5) {
            if (board[x].checked
                && board[x + 1].checked
                && board[x + 2].checked
                && board[x + 3].checked
                && board[x + 4].checked) {

                return true
            }
        }

        for (y in 0..4) {
            if (board[y].checked
                && board[y + 5].checked
                && board[y + 10].checked
                && board[y + 15].checked
                && board[y + 20].checked) {

                return true
            }
        }

        return false
    }

    val bingo = lines[0]
    var boards = mutableListOf<MutableList<Entry>>()

    for (i in 2..lines.size step 6) {
        val board = mutableListOf<Entry>()
        for (j in 0..4) {
            val line = lines[i + j]
            line.trim().split("\\s+".toRegex()).forEach {
                board.add(Entry(it.toInt(), false))
            }
        }
        boards.add(board)
    }

    bingo.split(",")
        .map {
            it.toInt()
        }
        .forEach { number ->
            boards = boards.map {
                val newBoard = it.map {
                    if (it.number == number) Entry(it.number, true)
                    else it
                }.toMutableList()

                if (boards.size == 1 && checkWin(newBoard)) {
                    val uncheckedSum = newBoard.filter { !it.checked }.sumOf { it.number }
                    println(uncheckedSum * number)
                    return
                }

                newBoard
            }.filter {
                !checkWin(it) || boards.size == 1
            }.also {

            }.toMutableList()
        }
}


puzzle4dot1()

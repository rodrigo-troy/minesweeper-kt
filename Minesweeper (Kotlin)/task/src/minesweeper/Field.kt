package minesweeper

import kotlin.math.abs
import kotlin.random.Random

class Field(rows: Int, columns: Int, mines: Int) {
    private val field = Array(rows) { CharArray(columns) { '.' } }
    private val minePositions = mutableListOf<Pair<Int, Int>>()

    init {
        var minesPlaced = 0
        while (minesPlaced < mines) {
            val row = Random.nextInt(rows)
            val col = Random.nextInt(columns)

            if (field[row][col] != 'X') {
                //field[row][col] = 'X'
                minePositions.add(Pair(row,
                                       col))
                minesPlaced++
            }
        }

        (0 until rows).forEach { row ->
            (0 until columns).forEach { col ->
                if (field[row][col] == '.') {
                    val minesAround = countMinesAround(row,
                                                       col)
                    if (minesAround > 0) {
                        field[row][col] = minesAround.toString().first()
                    }
                }
            }
        }
    }

    private fun countMinesAround(row: Int, col: Int): Int {
        return minePositions.count { (mineRow, mineCol) ->
            val rowDiff = abs(mineRow - row)
            val colDiff = abs(mineCol - col)
            rowDiff in 0..1 && colDiff in 0..1 && (rowDiff != 0 || colDiff != 0)
        }
    }

    fun printField() {
        println(" │123456789│")
        println("—│—————————│")

        field.forEachIndexed { rowIndex, row ->
            val rowString = row.joinToString("")
            println("${rowIndex + 1}│$rowString│")
        }

        println("—│—————————│")
    }

    fun processUserInput(row: Int, col: Int): UserInputResult {
        if (field[row][col].isDigit()) {
            return UserInputResult.NUMBER
        }

        markCell(row,
                 col)

        if (checkWinCondition()) {
            return UserInputResult.END_GAME
        }

        return UserInputResult.CONTINUE
    }

    private fun markCell(row: Int, col: Int) {
        field[row][col] = when (field[row][col]) {
            '.'  -> '*'
            '*'  -> '.'
            else -> field[row][col]
        }
    }

    private fun checkWinCondition(): Boolean {
        return minePositions.all { (row, col) -> field[row][col] == '*' } &&
                field.sumBy { row -> row.count { it == '*' } } == minePositions.size
    }
}

package minesweeper

import kotlin.random.Random

class Field(rows: Int, columns: Int, mines: Int) {
    private val field = Array(rows) { CharArray(columns) { '.' } }

    init {
        var minesPlaced = 0
        while (minesPlaced < mines) {
            val row = Random.nextInt(rows)
            val col = Random.nextInt(columns)

            if (isValidMinePlacement(field, row, col)) {
                field[row][col] = 'X'
                minesPlaced++
            }
        }
    }

    private fun isValidMinePlacement(minefield: Array<CharArray>, row: Int, col: Int): Boolean {
        if (minefield[row][col] == 'X') {
            return false
        }

        val rowOffsets = arrayOf(-1, 0, 1)
        val colOffsets = arrayOf(-1, 0, 1)

        for (rowOffset in rowOffsets) {
            for (colOffset in colOffsets) {
                val newRow = row + rowOffset
                val newCol = col + colOffset

                if (newRow in minefield.indices && newCol in 0 until minefield[newRow].size) {
                    if (minefield[newRow][newCol] == 'X') {
                        return false
                    }
                }
            }
        }

        return true
    }

    fun printField() {
        field.forEach { row ->
            println(row.joinToString(""))
        }
    }
}

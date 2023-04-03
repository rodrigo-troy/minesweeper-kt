package minesweeper

import kotlin.random.Random

class Field(rows: Int, columns: Int, mines: Int) {
    private val field = Array(rows) { CharArray(columns) { '.' } }

    init {
        var minesPlaced = 0
        while (minesPlaced < mines) {
            val row = Random.nextInt(rows)
            val col = Random.nextInt(columns)

            if (field[row][col] != 'X') {
                field[row][col] = 'X'
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
        return (-1..1).sumOf { i ->
            (-1..1).count { j ->
                val newRow = row + i
                val newCol = col + j

                if (i == 0 && j == 0) {
                    false
                } else {
                    newRow in field.indices && newCol in field[0].indices && field[newRow][newCol] == 'X'
                }
            }
        }
    }

    fun printField() {
        field.forEach { row ->
            println(row.joinToString(""))
        }
    }
}

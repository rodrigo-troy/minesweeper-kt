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
    }

    fun printField() {
        field.forEach { row ->
            println(row.joinToString(""))
        }
    }
}

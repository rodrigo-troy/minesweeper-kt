package minesweeper

import kotlin.math.abs
import kotlin.random.Random

/**
 * Represents a field of cells with mines.
 *
 * @param rows the number of rows in the field.
 * @param columns the number of columns in the field.
 * @param mines the number of mines to place in the field.
 */
class Field(rows: Int, columns: Int, mines: Int) {
    private val field = Array(rows) { CharArray(columns) { '.' } }
    private val minePositions = mutableListOf<Pair<Int, Int>>()

    /**
     * Initializes the field by placing mines randomly and calculating the numbers of mines around each cell.
     */
    init {
        var minesPlaced = 0
        while (minesPlaced < mines) {
            val row = Random.nextInt(rows)
            val col = Random.nextInt(columns)

            if (field[row][col] != 'X') {
                //field[row][col] = 'X'
                minePositions.add(
                    Pair(
                        row,
                        col
                    )
                )
                minesPlaced++
            }
        }

        (0 until rows).forEach { row ->
            (0 until columns).forEach { col ->
                if (field[row][col] == '.') {
                    val minesAround = countMinesAround(
                        row,
                        col
                    )
                    if (minesAround > 0) {
                        field[row][col] = minesAround.toString().first()
                    }
                }
            }
        }
    }

    /**
     * Counts the number of mines around a given cell.
     *
     * @param row the row index of the cell.
     * @param col the column index of the cell.
     * @return the number of mines around the cell.
     */
    private fun countMinesAround(row: Int, col: Int): Int {
        return minePositions.count { (mineRow, mineCol) ->
            val rowDiff = abs(mineRow - row)
            val colDiff = abs(mineCol - col)
            rowDiff in 0..1 && colDiff in 0..1 && (rowDiff != 0 || colDiff != 0)
        }
    }

    /**
     * Prints the current state of the field.
     */
    fun printField() {
        println(" │123456789│")
        println("—│—————————│")

        field.forEachIndexed { rowIndex, row ->
            val rowString = row.joinToString("")
            println("${rowIndex + 1}│$rowString│")
        }

        println("—│—————————│")
    }

    /**
     * Processes user input for a given cell and returns the result of the input.
     *
     * @param row the row index of the cell.
     * @param col the column index of the cell.
     * @return the result of the user input.
     */
    fun processUserInput(row: Int, col: Int): UserInputResult {
        if (field[col][row].isDigit()) {
            return UserInputResult.NUMBER
        }

        markCell(
            col,
            row
        )

        if (checkWinCondition()) {
            return UserInputResult.END_GAME
        }

        return UserInputResult.CONTINUE
    }

    /**
     * Toggles the mark on a given cell.
     *
     * @param col the column index of the cell.
     * @param row the row index of the cell.
     */
    private fun markCell(col: Int, row: Int) {
        field[col][row] = when (field[col][row]) {
            '.' -> '*'
            '*' -> '.'
            else -> field[col][row]
        }
    }

    /**
     * Checks if the win condition has been met.
     *
     * @return true if all mines are marked and all other cells are uncovered, false otherwise.
     */
    private fun checkWinCondition(): Boolean {
        return minePositions.all { (row, col) -> field[row][col] == '*' } &&
                field.sumBy { row -> row.count { it == '*' } } == minePositions.size
    }
}

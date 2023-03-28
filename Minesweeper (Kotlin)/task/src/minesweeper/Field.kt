package minesweeper

class Field(rows: Int, columns: Int, mines: Int) {
    private val field = Array(rows) { CharArray(columns) { '.' } }
    private val minePositions = mutableSetOf<Pair<Int, Int>>()

    init {
        repeat(mines) {
            var (row, column) = generateRandomPosition()
            while (minePositions.contains(row to column)) {
                row = (0 until rows).random()
                column = (0 until columns).random()
            }
            minePositions.add(row to column)
            field[row][column] = 'X'
        }
    }

    private fun generateRandomPosition() = (0 until field.size).random() to (0 until field[0].size).random()

    fun printField() {
        println(field.joinToString(separator = " ") { it.joinToString(separator = "") })
    }
}

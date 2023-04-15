package minesweeper

/**
 * Represents a field of cells with mines.
 *
 * @param rows the number of rows in the field.
 * @param columns the number of columns in the field.
 * @param mines the number of mines to place in the field.
 */
class Field(rows: Int, columns: Int, mines: Int) {
    private val field = Array(rows) { row ->
        Array(columns) { col ->
            Cell(col, row, CellState.UNEXPLORED, false)
        }
    }

    /**
     * Initializes the field by placing mines randomly and calculating the numbers of mines around each cell.
     */
    init {
        placeMines(mines)
        calculateMinesAround()
    }

    private fun calculateMinesAround() {
        for (row in field) {
            for (cell in row) {
                if (cell.isMine) {
                    for (i in -1..1) {
                        for (j in -1..1) {
                            if (i == 0 && j == 0) {
                                continue
                            }

                            val currentRow = cell.y + i
                            val currentCol = cell.x + j

                            if (currentRow in field.indices && currentCol in row.indices) {
                                field[currentRow][currentCol].incrementMinesAround()
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Places mines randomly in the field.
     *
     * @param mines the number of mines to place.
     */
    private fun placeMines(mines: Int) {
        var minesToPlace = mines

        while (minesToPlace > 0) {
            val row = (field.indices).random()
            val col = (0.until(field[row].size)).random()

            if (!field[row][col].isMine) {
                field[row][col].isMine = true
                minesToPlace--
            }
        }
    }

    /**
     * Prints the current state of the field.
     * Use the following symbols to represent each cell’s state:
     *
     * . as unexplored cells
     *
     * / as explored free cells without mines around it
     *
     * Numbers from 1 to 8 as explored free cells with 1 to 8 mines around them, respectively
     *
     * X as mines
     *
     * * as unexplored marked cells
     *
     * The first line of the field should contain the column numbers and the second line should contain the row numbers.
     * The first line should start with a space and the second line should start with a pipe.
     */
    fun printField() {
        println(" │123456789│")
        println("—│—————————│")

        for (row in field) {
            print("${field.indexOf(row) + 1}|")

            for (cell in row) {
                print(cell)
            }

            println("|")
        }


        println("—│—————————│")
    }

    /**
     * Processes user input for a given cell and returns the result of the input.
     * If the player explores a mine, print the field in its current state, with mines shown as X symbols. After that, output the message “You stepped on a mine and failed!”.
     * If the user wants to explore a cell, the cell should be explored if it is unexplored or marked.
     */
    fun processUserInput(x: Int, y: Int, userInput: UserInput): UserInputResult {
        val cell = field[y - 1][x - 1]

        return when (userInput) {
            UserInput.MINE -> {
                cell.swithMarked()
                UserInputResult.CONTINUE
            }

            UserInput.FREE -> {
                if (cell.isMine) {
                    cell.setExploredWithMine()
                    UserInputResult.STEPPED_ON_MINE
                } else {
                    this.exploreCell(cell)
                    UserInputResult.CONTINUE
                }
            }

            else -> {
                UserInputResult.CONTINUE
            }
        }
    }

    /**
     * Explores a given cell and its neighbors recursively.
     * If the cell has no mines around it, it should explore all of its neighbors.
     * If a cell has mines around it, it should only be explored.
     * If a cell is already explored, it should not be explored again.
     */
    private fun exploreCell(cell: Cell) {
        if (cell.isNotMine()) {
            cell.setExploredWithoutMine()

            if (cell.hasMinesAround()) {
                return
            } else {
                for (i in -1..1) {
                    for (j in -1..1) {
                        if (i == 0 && j == 0) {
                            continue
                        }

                        val currentRow = cell.y + i
                        val currentCol = cell.x + j

                        if (currentRow in field.indices && currentCol in field[currentRow].indices) {
                            val currentCell = field[currentRow][currentCol]

                            if (currentCell.wasExplored()) {
                                return
                            }

                            exploreCell(currentCell)
                        }
                    }
                }
            }
        } else {
            cell.setExploredWithoutMine()
            return
        }
    }

}

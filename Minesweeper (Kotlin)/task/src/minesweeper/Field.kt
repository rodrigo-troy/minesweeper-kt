package minesweeper

class Field(rows: Int, columns: Int, mines: Int) {
    private val field = Array(rows) { row ->
        Array(columns) { col ->
            Cell(col, row, CellState.UNEXPLORED, false)
        }
    }

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

                    if (isWin()) {
                        UserInputResult.WIN
                    }

                    UserInputResult.CONTINUE
                }
            }

            else -> {
                UserInputResult.CONTINUE
            }
        }
    }

    private fun isWin(): Boolean {
        val freeCellsCount = field.flatMap { it.toList() }.count { it.isNotMine() }
        val freeCellsExploredCount = field.flatMap { it.toList() }
            .filter { it.isNotMine() }
            .count { !it.isUnexplored() }
        val minesCount = field.flatMap { it.toList() }.count { it.isMine }
        val markedMinesCount = field.flatMap { it.toList() }
            .filter { it.isMine }
            .count { it.getState() == CellState.MARKED }

        if (minesCount == markedMinesCount) {
            return true
        }

        if (freeCellsCount == freeCellsExploredCount) {
            return true
        }

        return false
    }

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

                        val nextRow = cell.y + i
                        val nextCol = cell.x + j

                        if (nextRow in field.indices && nextCol in field[nextRow].indices) {
                            val nextCell = field[nextRow][nextCol]

                            if (nextCell.wasExplored()) {
                                continue
                            }

                            exploreCell(nextCell)
                        }
                    }
                }
            }
        } else {
            cell.setExploredWithMine()
            return
        }
    }
}

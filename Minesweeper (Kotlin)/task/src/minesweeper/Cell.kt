package minesweeper

/**
 * Created with IntelliJ IDEA.
$ Project: minesweeper-kt
 * User: rodrigotroy
 * Date: 15-04-23
 * Time: 12:35
 */
class Cell(val x: Int, val y: Int, private var state: CellState, var isMine: Boolean) {
    private var minesAround = 0

    fun incrementMinesAround() {
        minesAround++
    }

    fun hasMinesAround() = minesAround > 0

    fun wasExplored() = state == CellState.EXPLORED_WITH_MINE || state == CellState.EXPLORED_WITHOUT_MINE

    private fun isUnexplored() = state == CellState.UNEXPLORED

    fun isNotMine() = !isMine

    fun swithMarked() {
        state = if (this.isUnexplored()) {
            CellState.MARKED
        } else {
            CellState.UNEXPLORED
        }
    }

    fun setExploredWithoutMine() {
        state = CellState.EXPLORED_WITHOUT_MINE
    }

    fun setExploredWithMine() {
        state = CellState.EXPLORED_WITH_MINE
    }

    override fun toString(): String {
        return when (state) {
            CellState.UNEXPLORED -> "."
            CellState.MARKED -> "*"
            CellState.EXPLORED_WITH_MINE -> "X"
            CellState.EXPLORED_WITHOUT_MINE -> if (minesAround == 0) "/" else minesAround.toString()
            else -> "."
        }
    }
}

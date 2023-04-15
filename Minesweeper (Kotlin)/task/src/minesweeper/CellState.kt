package minesweeper

/**
 * Created with IntelliJ IDEA.
$ Project: minesweeper-kt
 * User: rodrigotroy
 * Date: 15-04-23
 * Time: 12:35
 *
 * Use the following symbols to represent each cell’s state:
 *
 * "." as unexplored cells
 * "/" as explored free cells without mines around it
 * Numbers from 1 to 8 as explored free cells with 1 to 8 mines around them, respectively
 * "X" as mines
 * "*" as unexplored marked cells
 */
enum class CellState {
    MARKED,
    UNEXPLORED,
    EXPLORED_WITH_MINE,
    EXPLORED_WITHOUT_MINE,
    EXPLORED_WITH_MINES_AROUND
}

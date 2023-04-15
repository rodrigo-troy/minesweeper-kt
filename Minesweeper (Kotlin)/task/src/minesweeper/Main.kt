package minesweeper

/**
 *
 * The game starts with asking the player for the number of mines.
 *
 * Ask the player for their next move with the message “Set/unset mine marks or claim a cell as free:”, treat the player's move according to the rules, and print the new minefield state.
 *
 * Ask for the player's next move until the player wins or steps on a mine.
 *
 * The player's input contains a pair of cell coordinates and a command: mine to mark or unmark a cell, free to explore a cell.
 *
 * If the player explores a mine, print the field in its current state, with mines shown as X symbols. After that, output the message “You stepped on a mine and failed!”.
 *
 */
fun main() {
    println("How many mines do you want on the field?")
    val mines = readln().toInt()

    val field = Field(
        9,
        9,
        mines
    )

    while (true) {
        field.printField()
        println("Set/unset mine marks or claim a cell as free:")
        val (x, y, command) = readln().split(" ")

        val userInput = UserInput.fromString(command)

        if (userInput == UserInput.UNDEFINED) {
            println("Invalid input")
            continue
        }

        when (field.processUserInput(x.toInt(), y.toInt(), userInput)) {
            UserInputResult.WIN -> {
                println("Congratulations! You found all the mines!")
                return
            }

            UserInputResult.STEPPED_ON_MINE -> {
                println("You stepped on a mine and failed!")
                field.printField()
                return
            }

            UserInputResult.CONTINUE -> {
                continue
            }
        }
    }
}

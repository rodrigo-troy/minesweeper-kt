package minesweeper

fun main() {
    println("How many mines do you want on the field?")
    val mines = readln().toInt()

    val field = Field(9,
                      9,
                      mines)

    while (true) {
        field.printField()
        println("Set/delete mines marks (x and y coordinates):")
        val (row, col) = readln().split(" ").map { it.toInt() - 1 }

        when (field.processUserInput(row,
                                     col)) {
            UserInputResult.NUMBER   -> println("There is a number here!")
            UserInputResult.END_GAME -> {
                field.printField()
                println("Congratulations! You found all the mines!")
                break
            }

            UserInputResult.CONTINUE -> Unit
        }
    }
}

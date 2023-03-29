package minesweeper

fun main() {
    println("How many mines do you want on the field?")
    val mines = readln().toInt()

    val field = Field(9, 9, mines)
    field.printField()
}

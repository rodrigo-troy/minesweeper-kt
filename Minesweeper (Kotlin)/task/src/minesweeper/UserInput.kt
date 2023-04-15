package minesweeper

/**
 * Created with IntelliJ IDEA.
$ Project: minesweeper-kt
 * User: rodrigotroy
 * Date: 15-04-23
 * Time: 16:43
 */
enum class UserInput(private val command: String) {
    FREE("free"),
    MINE("mine"),
    UNDEFINED("");

    companion object {
        private val inputRegex = Regex("^(free|mine)\$")
        fun fromString(command: String): UserInput {
            val matchResult = inputRegex.matches(command)

            if (!matchResult) {
                return UNDEFINED
            }

            return when (command) {
                FREE.command -> FREE
                MINE.command -> MINE
                else -> UNDEFINED
            }
        }
    }
}

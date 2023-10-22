package indigo

import java.util.Scanner

interface Player {
    val name: String
    var hand: MutableList<String>
    var score: Int
    var cards: Int

    fun playRound(table: Table, opponent: Player, scanner: Scanner): Int
}
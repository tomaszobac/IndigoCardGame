package indigo

import java.util.Scanner

class User(override val name: String) : Player {
    override var hand: MutableList<String> = mutableListOf()
    override var score: Int = 0
    override var cards: Int = 0
    
    override fun playRound(table: Table, opponent: Player, scanner: Scanner): Int {
        if (table.placedCards.isEmpty())
            println("No cards on the table")
        else
            println("${table.placedCards.size} cards on the table, and the top card is ${table.placedCards.last()}")
        println("Cards in hand: " + this.hand.withIndex().joinToString(" ") { (index,it) -> "${index + 1})$it" })
        println("Choose a card to play (1-${this.hand.size}):")

        var cardNumber = scanner.nextLine()
        while (cardNumber !in listOf("1","2","3","4","5","6") || cardNumber.toInt() !in 1..this.hand.size) {
            if (cardNumber == "exit") {
                println("Game Over")

                return 2
            }
            println("Choose a card to play (1-${this.hand.size}):")
            cardNumber = scanner.nextLine()
        }

        var win = false
        if (table.placedCards.isNotEmpty()) {
            win = table.placedCards.last().last() == this.hand[cardNumber.toInt() - 1].last() ||
                    table.placedCards.last().dropLast(1) == this.hand[cardNumber.toInt() - 1].dropLast(1)
        }
        table.placedCards.add(this.hand[cardNumber.toInt() - 1])
        this.hand.removeAt(cardNumber.toInt() - 1)

        return if (win) 1 else 0
    }
}
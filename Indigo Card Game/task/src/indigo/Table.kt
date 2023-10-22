package indigo

class Table {
    val deck: MutableList<String> = mutableListOf()
    var placedCards: MutableList<String> = mutableListOf()

    fun prepareDeck() {
        val ranks: List<String> = listOf("A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K")

        val suits: Map<String, List<String>> = mapOf(
            Pair("♦", ranks),
            Pair("♥", ranks),
            Pair("♠", ranks),
            Pair("♣", ranks))

        suits.entries.forEach {entry -> entry.value.forEach { deck += "${it}${entry.key}" } }
        deck.shuffle()
    }

    fun roundWon(player: Player, opponent: Player) {
        player.cards += this.placedCards.size
        this.placedCards.forEach { if (it.dropLast(1) in listOf("A", "10", "J", "Q", "K")) player.score++ }
        this.placedCards.clear()
        println("${player.name} wins cards")
        if (player.name == "Player") {
            println("Score: Player ${player.score} - Computer ${opponent.score}")
            println("Cards: Player ${player.cards} - Computer ${opponent.cards}")
        } else {
            println("Score: Player ${opponent.score} - Computer ${player.score}")
            println("Cards: Player ${opponent.cards} - Computer ${player.cards}")
        }
    }
}
package indigo

import java.util.Scanner

class Computer(override val name: String) : Player {
    override var hand: MutableList<String> = mutableListOf()
    override var score: Int = 0
    override var cards: Int = 0

    override fun playRound(table: Table, opponent: Player, scanner: Scanner): Int {
        if (table.placedCards.isEmpty())
            println("No cards on the table")
        else
            println("${table.placedCards.size} cards on the table, and the top card is ${table.placedCards.last()}")

        println(this.hand.joinToString(" "))

        return playAlgorithm(table)
    }

    private fun playAlgorithm(table: Table): Int {
        if (this.hand.size == 1) {
            var win = false
            if (table.placedCards.isNotEmpty()) {
                win = table.placedCards.last().last() == this.hand.first().last() ||
                        table.placedCards.last().dropLast(1) == this.hand.first().dropLast(1)
            }
            throwCard(table, this.hand.first())
            return if (win) 1 else 0
        }

        if (table.placedCards.isNotEmpty()) {
            val candidates: MutableList<String> = mutableListOf()

            this.hand.forEach {
                if (table.placedCards.last().last() == it.last() ||
                    table.placedCards.last().dropLast(1) == it.dropLast(1)) candidates += it
            }

            if (candidates.size == 0) {
                noWinOption(table)
                return 0
            }

            if (candidates.size == 1) {
                throwCard(table, candidates[0])
                return 1
            }

            winOption(table, candidates)
            return 1

        }

        noWinOption(table)
        return 0
    }

    private fun noWinOption(table: Table) {
        this.hand.filter {
            val occurrences = this.hand.count { item -> item.last() == it.last() }
            occurrences > 1
        }.also {
            if (it.isNotEmpty()) {
                throwCard(table, it.random())
                return
            } }

        if (this.hand.size <= 4) {
            this.hand.filter {
                val occurrences = this.hand.count { item -> item.dropLast(1) == it.dropLast(1) }
                occurrences > 1
            }.also {
                if (it.isNotEmpty()) {
                    throwCard(table, it.random())
                    return
                } }
        }

        throwCard(table, this.hand.random())
        return
    }

    private fun winOption(table: Table, candidates: MutableList<String>) {
        candidates.filter {
            val occurrences = candidates.count { item -> item.last() == it.last() }
            occurrences > 1
        }.also {
            if (it.isNotEmpty()) {
                throwCard(table, it.random())
                return
        } }

        candidates.filter {
            val occurrences = candidates.count { item -> item.dropLast(1) == it.dropLast(1) }
            occurrences > 1
        }.also {
            if (it.isNotEmpty()) {
                throwCard(table, it.random())
                return
        } }

        throwCard(table, candidates.random())
        return
    }

    private fun throwCard(table: Table, card: String) {
        table.placedCards.add(card)
        this.hand.remove(card)
        println("Computer plays $card")
    }
}
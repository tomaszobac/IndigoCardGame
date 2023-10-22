package indigo

import java.util.Scanner

fun main() {
    val scanner = Scanner(System.`in`)
    val table = Table()
    val player = User("Player")
    val cpu = Computer("Computer")

    table.prepareDeck()

    var playerPlays: String = setupGame(scanner, table, player, cpu)
    val firstPlayer = if (playerPlays == "yes") player else cpu
    var lastWinner: Player? = null

    while (true) {
        if (playerPlays == "no") {
            when (cpu.playRound(table, player, scanner)) {
                1 -> {
                    table.roundWon(cpu, player)
                    lastWinner = cpu
                }
            }
            when (checkHand(table, cpu, player)) {
                1 -> {
                    sumUp(table, player, cpu, firstPlayer, lastWinner)
                    return
                }
            }
            playerPlays = "yes"
            continue
        }
        when (player.playRound(table, cpu, scanner)) {
            1 -> {
                table.roundWon(player, cpu)
                lastWinner = player
            }
            2 -> {
                println("Game Over")
                return
            }
        }
        when (checkHand(table, player, cpu)) {
            1 -> {
                sumUp(table, player, cpu, firstPlayer, lastWinner)
                return
            }
        }
        playerPlays = "no"
    }
}

fun setupGame(scanner: Scanner, table: Table, player: User, cpu: Computer): String {
    println("Indigo Card Game")
    println("Play first?")

    var playerPlays = scanner.nextLine()
    while (playerPlays !in listOf("yes","no")) {
        println("Play first?")
        playerPlays = scanner.nextLine()
    }

    println("Initial cards on the table: " + table.deck.subList(0, 4).joinToString(" "))

    table.placedCards = table.deck.subList(0, 4).toMutableList()
    table.deck.subList(0, 4).clear()

    player.hand = table.deck.subList(0, 6).toMutableList()
    table.deck.subList(0, 6).clear()

    cpu.hand = table.deck.subList(0, 6).toMutableList()
    table.deck.subList(0, 6).clear()

    return playerPlays
}

fun checkHand(table: Table, player: Player, opponent: Player): Int {
    if (player.hand.isEmpty()) {
        if (table.deck.isEmpty()) {
            if (opponent.hand.isEmpty())
                return 1
            return 0
        }

        player.hand = table.deck.subList(0, 6).toMutableList()
        table.deck.subList(0, 6).clear()

        return 0
    }

    return 0
}

fun sumUp(table: Table, player: Player, opponent: Player, firstPlayer: Player, lastWinner: Player?) {
    if (table.placedCards.isEmpty())
        println("No cards on the table")
    else
        println("${table.placedCards.size} cards on the table, and the top card is ${table.placedCards.last()}")

    if (lastWinner == null) {
        firstPlayer.cards += table.placedCards.size
        table.placedCards.forEach { if (it.dropLast(1) in listOf("A", "10", "J", "Q", "K")) firstPlayer.score++ }
    } else {
        lastWinner.cards += table.placedCards.size
        table.placedCards.forEach { if (it.dropLast(1) in listOf("A", "10", "J", "Q", "K")) lastWinner.score++ }
    }

    if (player.cards == opponent.cards)
        firstPlayer.score += 3
    else if (player.cards > opponent.cards)
        player.score += 3
    else
        opponent.score += 3

    println("Score: Player ${player.score} - Computer ${opponent.score}")
    println("Cards: Player ${player.cards} - Computer ${opponent.cards}")
    println("Game Over")
}
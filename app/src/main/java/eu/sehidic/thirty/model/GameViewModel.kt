package eu.sehidic.thirty.model

import androidx.lifecycle.ViewModel

const val MAX_THROWS = 2 // Constant instead of magic numbers :)

/**
 * Game logic controller class used by MainActivity.
 *
 */
class GameViewModel : ViewModel() {
    private var player = Player()
    private var currentChoice: Choice? = null
    private var rounds: MutableList<Round> = ArrayList()
    private var availableChoices: MutableList<Choice> = Choice.values().toMutableList()
    var round = 1
    private var throws = 0

    fun throwDice() {
        player.rollDice()
        throws++
    }

    fun getDice(): Array<Die> {
        return player.dice
    }

    fun getKeptDice(): Array<Die> {
        return player.dice.filter { it.keep }.toTypedArray()
    }

    fun getRounds(): Array<Round> {
        return rounds.toTypedArray()
    }

    fun hasCurrentChoice(): Boolean {
        return currentChoice != null
    }

    fun roundDone() {
        throws = 0
        currentChoice?.let {
            Round(round, it, player.getCurrScore()).also {
                rounds.add(it)
            }
        }
        player.clearScore()
        round += 1
        availableChoices.remove(currentChoice)
        currentChoice = null
        player.resetDice()
    }

    fun resetGame() {
        player = Player()
        round = 1
        throws = 0
        availableChoices = Choice.values().toMutableList()
        rounds = ArrayList()
    }

    fun getAvailableChoices(): Array<Choice> {
        return availableChoices.toTypedArray()
    }

    fun hasThrown(): Boolean {
        return throws > 0
    }

    fun getTotalScore(): Int {
        return player.getCurrScore()
    }

    fun toggleKeep(index: Int) {
        player.toggleKeep(index)
    }

    fun canThrow(): Boolean {
        return throws - 1 < MAX_THROWS
    }

    fun unKeepAllDice() {
        for (die in player.dice) {
            die.keep = false
        }
    }

    fun addScore(target: Choice, dice: Array<Die>): Int {
        val score = getScoreFromDice(target, dice)
        currentChoice = target
        if (score.score > 0) {
            player.useKeptDice()
        }
        unKeepAllDice()
        player.addScore(score)
        return score.score
    }

    fun getGameState(): GameState {
        return GameState(player, currentChoice, rounds, availableChoices, round, throws)
    }

    fun setGameState(gameState: GameState) {
        this.player = gameState.player
        this.currentChoice = gameState.currentChoice
        this.rounds = gameState.rounds
        this.availableChoices = gameState.availableChoices
        this.round = gameState.round
        this.throws = gameState.throws
    }
}
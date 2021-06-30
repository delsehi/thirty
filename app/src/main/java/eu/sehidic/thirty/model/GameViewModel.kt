package eu.sehidic.thirty.model

import androidx.lifecycle.ViewModel

const val MAX_THROWS = 2

/**
 * Game logic controller class used by MainActivity.
 * @author Delfi Sehidic
 */
class GameViewModel : ViewModel() {
    private var player = Player()
    private var currentChoice: Choice? = null
    private var rounds: MutableList<Round> = ArrayList()
    private var availableChoices: MutableList<Choice> = Choice.values().toMutableList()
    var round = 1
    private var throws = 0

    /**
     * Throw the dice not selected for keeping and increase number of throws.
     */
    fun throwDice() {
        player.rollDice()
        throws++
    }

    /**
     * Returns the player's hand of dice.
     * @return All the player's dice.
     */
    fun getDice(): Array<Die> {
        return player.dice
    }

    /**
     * Returns the subset of dice that has been selected for keeping or scoring.
     * @return The selected subset of dice.
     */
    fun getKeptDice(): Array<Die> {
        return player.dice.filter { it.keep }.toTypedArray()
    }

    /**
     * Get all finished rounds.
     * @return An array of round objects.
     */
    fun getRounds(): Array<Round> {
        return rounds.toTypedArray()
    }

    /**
     * Returns true if the user has attempted to score some dice.
     * @return if the user has attempted scoring.
     */
    fun hasCurrentChoice(): Boolean {
        return currentChoice != null
    }

    /**
     * Resets the number of throws, creates a round and saves it,
     * resets the player's current score for this round,
     * removes the used scoring choice and resets the dice.
     */
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

    /**
     * Sets the game state to a clean slate.
     */
    fun resetGame() {
        player = Player()
        round = 1
        throws = 0
        availableChoices = Choice.values().toMutableList()
        rounds = ArrayList()
    }

    /**
     * Get the scoring choices that have not yet been used.
     * @return Array of available scoring choices.
     */
    fun getAvailableChoices(): Array<Choice> {
        return availableChoices.toTypedArray()
    }

    /**
     * Returns true if the user has thrown the dice at least once.
     * @return number of throws is larger than 0.
     */
    fun hasThrown(): Boolean {
        return throws > 0
    }

    /**
     * Returns the player's current score for this particular round.
     * @return Player's current score.
     */
    fun getTotalScore(): Int {
        return player.getCurrScore()
    }

    /**
     * Toggles whether a particular die is selected or not by its index position (0-5)
     * @param index The index of the toggled die.
     */
    fun toggleKeep(index: Int) {
        player.toggleKeep(index)
    }

    /**
     * Returns true if the player has not thrown three times yet.
     * @return has thrown at least once or not.
     */
    fun canThrow(): Boolean {
        return throws - 1 < MAX_THROWS
    }

    /**
     * Makes all the dice unselected.
     */
    fun unKeepAllDice() {
        for (die in player.dice) {
            die.keep = false
        }
    }

    /**
     * Attempts to add score.
     * @param target The scoring method (e.g LOW, FOUR, etc)
     * @param dice A subset of dice selected for scoring.
     * @return The result of the attempt to get the target score (0-12).
     */
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

    /**
     * Creates a GameState object containing the state of the game.
     * @return The state of the game.
     */
    fun getGameState(): GameState {
        return GameState(player, currentChoice, rounds, availableChoices, round, throws)
    }

    /**
     * Updates the state of the game from a GameState object.
     * @param gameState The state of the game.
     */
    fun setGameState(gameState: GameState) {
        this.player = gameState.player
        this.currentChoice = gameState.currentChoice
        this.rounds = gameState.rounds
        this.availableChoices = gameState.availableChoices
        this.round = gameState.round
        this.throws = gameState.throws
    }
}
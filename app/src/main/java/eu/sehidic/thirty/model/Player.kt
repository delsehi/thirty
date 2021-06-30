package eu.sehidic.thirty.model

/**
 * A player class that keeps track of the player's dice and
 * current score for a particular round.
 * @author Delfi Sehidic
 */
import java.io.Serializable

class Player : Serializable {
    val dice = Array(6) { Die() } // All values set to 0 initially
    private val score: MutableList<Score> = ArrayList() // Empty initially

    /**
     * Toggles whether the die should be thrown or not the next round.
     * @param index The index position of the die (0-5).
     */
    fun toggleKeep(index: Int) {
        this.dice[index].keep = !this.dice[index].keep
    }

    /**
     * Takes a score object and adds it to the score array.
     * @param newScore The score
     */
    fun addScore(newScore: Score) {
        this.score.add(newScore)
    }

    /**
     * Clears the current score for the player.
     * Used for cleanup between rounds.
     */
    fun clearScore() {
        this.score.clear()
    }

    /**
     * Resets all dice to no face value, not used for scoring and not selected.
     */
    fun resetDice() {
        for (die in dice) {
            die.value = 0
            die.keep = false
            die.used = false
        }
    }

    /**
     * Returns the current score for this round.
     * The sum of each successful attempt to score the target with dice.
     * Should be reset between rounds.
     * @see clearScore()
     * @return Score of current round.
     */
    fun getCurrScore(): Int {
        return score.fold(0) { acc, e -> acc + e.score }
    }

    /**
     * Makes all selected dice used.
     */
    fun useKeptDice() {
        for (die in this.dice) {
            if (die.keep) die.used = true
        }
    }

    /**
     * Throws all dice that has not been selected for keeping.
     */
    fun rollDice() {
        for (die in this.dice) {
            if (!die.keep) {
                die.roll()
            }
        }
    }

}
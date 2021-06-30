package eu.sehidic.thirty.model

import java.io.Serializable

class Player: Serializable {
    val dice = Array(6) { Die() }
    private val score: MutableList<Score> = ArrayList()


    fun toggleKeep(index: Int) {
        // Toggle whether the die should be thrown or not the next round.
        this.dice[index].keep = !this.dice[index].keep
    }

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

    fun useKeptDice() {
        for (die in this.dice) {
            if (die.keep) die.used = true
        }
    }

    fun rollDice() {
        // Roll all dice that the user does not want to keep
        for (die in this.dice) {
            if (!die.keep) {
                die.roll()
            }
        }
    }

}
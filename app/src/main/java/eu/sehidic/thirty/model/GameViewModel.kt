package eu.sehidic.thirty.model

import androidx.lifecycle.ViewModel

const val MAX_THROWS = 2 // Constant instead of magic numbers :)

/**
 * Controller class used by MainActivity.
 *
 */
class GameViewModel: ViewModel() {
    private val player = Player()
    var round = 0
    var throws = 0

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

    fun roundDone() {
        throws = 0
        round += 1
        player.resetDice()
    }

    fun getTotalScore(): Int {
        return player.getTotalScore()
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
        if (score.score > 0) {
            player.useKeptDice()
        }
        unKeepAllDice()
        player.addScore(score)
        return score.score
    }


}
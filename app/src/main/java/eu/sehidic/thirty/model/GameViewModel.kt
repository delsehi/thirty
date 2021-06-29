package eu.sehidic.thirty.model

import androidx.lifecycle.ViewModel

const val MAX_THROWS = 2 // Constant instead of magic numbers :)

/**
 * Controller class used by MainActivity.
 *
 */
class GameViewModel: ViewModel() {
    private val player = Player()
    private var currentChoice: Choice? = null
    private val rounds: MutableList<Round> = ArrayList()
    private val availableChoices: MutableList<Choice> = Choice.values().toMutableList()
    var round = 1
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

    fun getAvailableChoices(): Array<Choice> {
        return availableChoices.toTypedArray()
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


}
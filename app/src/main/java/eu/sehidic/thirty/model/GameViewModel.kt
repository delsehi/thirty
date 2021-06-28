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
    fun getTotalScore(): Int {
        return player.getTotalScore()
    }
    fun toggleKeep(index: Int) {
        player.toggleKeep(index)
    }
    fun canThrow(): Boolean {
        return throws < MAX_THROWS
    }
    fun unKeepAllDice() {
        for (die in player.dice) {
            die.keep = false
        }
    }
    fun addScore(target: Choice) {
        when (target) {
            Choice.LOW -> TODO()
            Choice.FOUR -> TODO()
            Choice.FIVE -> TODO()
            Choice.SIX -> TODO()
            Choice.SEVEN -> TODO()
            Choice.EIGHT -> TODO()
            Choice.NINE -> TODO()
            Choice.TEN -> TODO()
            Choice.ELEVEN -> TODO()
            Choice.TWELVE -> TODO()
        }
    }


}
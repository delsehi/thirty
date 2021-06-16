package eu.sehidic.thirty.model

import androidx.lifecycle.ViewModel

class GameViewModel: ViewModel() {
    val player = Player()
    var round = 0
    var throws = 0

    fun throwDice() {
        player.rollDice()
        throws++
    }
    fun getDice(): Array<Die> {
        return player.dice
    }
    fun toggleKeep(index: Int) {
        player.toggleKeep(index)
    }

}
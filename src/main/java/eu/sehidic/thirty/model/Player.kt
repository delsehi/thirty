package eu.sehidic.thirty.model

class Player {
    val dice = Array(6) { Die() }

    fun keep(index: Int) {
        // Keep a certain die, will not be thrown the next round
        this.dice[index].keep = true
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
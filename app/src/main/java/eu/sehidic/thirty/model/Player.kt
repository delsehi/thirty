package eu.sehidic.thirty.model

class Player {
    val dice = Array(6) { Die() }
    var score = 0

    fun toggleKeep(index: Int) {
        // Toggle whether the die should be thrown or not the next round.
        this.dice[index].keep = !this.dice[index].keep
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
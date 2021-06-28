package eu.sehidic.thirty.model

class Player {
    val dice = Array(6) { Die() }
    private val score = emptyArray<Score>()

    fun toggleKeep(index: Int) {
        // Toggle whether the die should be thrown or not the next round.
        this.dice[index].keep = !this.dice[index].keep
    }

    fun getTotalScore(): Int {
        return score.fold(0) { acc, e -> acc + e.score }
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
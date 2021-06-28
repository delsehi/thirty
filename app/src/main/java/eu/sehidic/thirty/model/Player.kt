package eu.sehidic.thirty.model

class Player {
    val dice = Array(6) { Die() }
    private val score: MutableList<Score> = ArrayList()

    fun toggleKeep(index: Int) {
        // Toggle whether the die should be thrown or not the next round.
        this.dice[index].keep = !this.dice[index].keep
    }

    fun addScore(newScore: Score) {
        this.score.add(newScore)
    }

    fun resetDice() {
        for (die in dice) {
            die.value = 0
            die.keep = false
            die.used = false
        }
    }

    fun getTotalScore(): Int {
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
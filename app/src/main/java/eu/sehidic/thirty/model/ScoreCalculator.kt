package eu.sehidic.thirty.model

/**
 * Package methods for calculating score. (Static methods)
 */
fun getScoreFromDice(target: Choice, dice: Array<Die>): Score {
    return when (target) {
        Choice.LOW -> getLowScore(dice)
        else -> targetNotLow(target, dice)
    }
}

private fun getLowScore(dice: Array<Die>): Score {
    val sum = dice.filter { it.value < 4 }.fold(0) { acc, die -> acc + die.value }
    return Score(sum)
}

private fun targetNotLow(target: Choice, dice: Array<Die>): Score {
    val sum = dice.fold(0) { acc, e -> acc + e.value }
    return if (sum == choiceAsInt(target)) {
        Score(sum)
    } else {
        Score(0)
    }
}

fun choiceAsInt(choice: Choice): Int {
    return when (choice) {
        Choice.FOUR -> 4
        Choice.FIVE -> 5
        Choice.SIX -> 6
        Choice.SEVEN -> 7
        Choice.EIGHT -> 8
        Choice.NINE -> 9
        Choice.TEN -> 10
        Choice.ELEVEN -> 11
        Choice.TWELVE -> 12
        Choice.LOW -> -1
    }
}
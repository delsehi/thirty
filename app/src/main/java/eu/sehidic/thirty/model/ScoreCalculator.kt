package eu.sehidic.thirty.model

/**
 * Package methods for calculating score. (Static methods)
 * @author Delfi Sehidic
 */

/**
 * Takes a subset of the players dice and a scoring method and calculates the score.
 * @param target A choice enum
 * @param dice The hand of dice
 * @return The calculated score (0-12).
 */
fun getScoreFromDice(target: Choice, dice: Array<Die>): Score {
    return when (target) {
        Choice.LOW -> getLowScore(dice)
        else -> targetNotLow(target, dice)
    }
}

/**
 * Returns the score from a set of dice when using the LOW scoring method.
 * Dice with the values 1-3 are summed as the score.
 * @param dice The dice used for scoring.
 * @return The resulting score.
 */
private fun getLowScore(dice: Array<Die>): Score {
    val sum = dice.filter { it.value < 4 }.fold(0) { acc, die -> acc + die.value }
    return Score(sum)
}

/**
 * Calculates the score when the scoring method is not LOW (e.g. FOUR, TWELVE).
 * If the dice sum to the target score, that score is returned. Otherwise 0 is returned.
 * @param target A choice enum
 * @param dice The hand of dice
 * @return The calculated score (0-12).
 */
private fun targetNotLow(target: Choice, dice: Array<Die>): Score {
    val sum = dice.fold(0) { acc, e -> acc + e.value }
    return if (sum == choiceAsInt(target)) {
        Score(sum)
    } else {
        Score(0)
    }
}

/**
 * Convert a Choice enum to an integer.
 * LOW should never be used.
 * @param choice The enum representing a scoring method
 * @return An integer (4-12).
 */
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
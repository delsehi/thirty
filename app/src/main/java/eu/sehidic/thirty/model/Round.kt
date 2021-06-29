package eu.sehidic.thirty.model

import java.io.Serializable

/**
 * Data class for each of the 10 rounds of gameplay.
 * @param round The round (1-10)
 * @param choice What scoring choice was used (e.g. LOW, FOUR, FIVE, etc)
 * @param score What the final score was from the round.
 */
data class Round(val round: Int, val choice: Choice, val score: Int = 0): Serializable
package eu.sehidic.thirty.model

import java.io.Serializable

data class GameState(
    val player: Player,
    var currentChoice: Choice?,
    val rounds: MutableList<Round>,
    var availableChoices: MutableList<Choice>,
    val round: Int,
    val throws: Int
) : Serializable
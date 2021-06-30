package eu.sehidic.thirty.model

/**
 * Die class. Face value set to 0 intially. Can be thrown to get a random new face value (1-6).
 * Keeps track of whether it has been selected/kept or not and if it has been used for scoring
 * during a round.
 * @author Delfi Sehidic
 */
import java.io.Serializable

class Die(newValue: Int = 0): Serializable {
    var value = newValue // 0 if not set explicitly
    var used = false
    var keep = false

    /**
     * Roll this die so it gets a random value between 1 and 6.
     */
    fun roll() {
        value = (1..6).random()
    }
}
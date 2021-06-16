package eu.sehidic.thirty

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import eu.sehidic.thirty.model.Die
import eu.sehidic.thirty.model.GameViewModel

private const val ROUND = "ROUND"
private const val THROWS = "THROWS"

class MainActivity : AppCompatActivity() {
    private lateinit var spinner: Spinner
    private lateinit var throwButton: Button
    private lateinit var die1: ImageView
    private lateinit var die2: ImageView
    private lateinit var die3: ImageView
    private lateinit var diceImages: Array<ImageView>


    private val gvm: GameViewModel by lazy {
        ViewModelProviders.of(this).get(GameViewModel::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        spinner = findViewById(R.id.spinner) // Dropdown menu for LOW, four, etc
        throwButton = findViewById(R.id.throw_button)
        die1 = findViewById(R.id.die1)
        die2 = findViewById(R.id.die2)
        die3 = findViewById(R.id.die3)
        diceImages = arrayOf(die1, die2, die3)

        var round = savedInstanceState?.getInt(ROUND) ?: 0
        var throws = savedInstanceState?.getInt(THROWS) ?: 0
        renderDice(gvm.getDice())

        throwButton.setOnClickListener {
            gvm.throwDice()
            val dice = gvm.getDice()
            renderDice(dice)
        }

        makeDieKeepable(die1, 0)
        makeDieKeepable(die2, 1)
        makeDieKeepable(die3, 2)
    }

    /**
     * Saves the current UI to a bundle.
     */
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(ROUND, gvm.round) // Save the current round (0-10)
        outState.putInt(THROWS, gvm.throws)
    }

    /**
     * Takes an array of dice and updates their image representations.
     * @param dice The dice objects
     */
    private fun renderDice(dice: Array<Die>) {
        for ((index, diceImage) in diceImages.withIndex()) {
            diceImage.setImageResource(getDieResource(dice[index].value, dice[index].keep))
        }
    }

    /**
     * Get the drawable resource from a die value.
     * @param dieValue The value (1-6) of the die.
     * @return The resId of the resource.
     */
    private fun getDieResource(dieValue: Int, isKept: Boolean): Int {
        if (isKept) {
            return when (dieValue) {
                1 -> R.drawable.one_grey
                2 -> R.drawable.two_grey
                3 -> R.drawable.three_grey
                4 -> R.drawable.four_grey
                5 -> R.drawable.five_grey
                6 -> R.drawable.six_grey
                else -> R.drawable.none
            }
        }
        return when (dieValue) {
            1 -> R.drawable.one
            2 -> R.drawable.two
            3 -> R.drawable.three
            4 -> R.drawable.four
            5 -> R.drawable.five
            6 -> R.drawable.six
            else -> R.drawable.none
        }
    }

    private fun makeDieKeepable(die: ImageView, index: Int) {
        die.setOnClickListener {
            if (gvm.throws > 0) { // Must've thrown at least once
                gvm.toggleKeep(index)
                renderDice(gvm.getDice())
                Toast.makeText(this, "Keeping die #" + (index + 1), Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Cannot keep it. ", Toast.LENGTH_SHORT).show()
            }
        }
    }

}
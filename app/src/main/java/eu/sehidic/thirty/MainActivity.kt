package eu.sehidic.thirty

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Spinner
import androidx.lifecycle.ViewModelProviders
import eu.sehidic.thirty.model.Die
import eu.sehidic.thirty.model.GameViewModel

private const val ROUND = "ROUND"
private const val THROWS = "THROWS"
private const val THROWVISIBLE = "THROWVISIBLE"
private const val SCOREMENUVISIBLE = "SCOREMENUVISIBLE"

class MainActivity : AppCompatActivity() {
    private lateinit var spinner: Spinner
    private lateinit var throwButton: Button
    private lateinit var addScore: Button
    private lateinit var roundDone: Button
    private lateinit var die1: ImageView
    private lateinit var die2: ImageView
    private lateinit var die3: ImageView
    private lateinit var die4: ImageView
    private lateinit var die5: ImageView
    private lateinit var die6: ImageView
    private lateinit var diceImages: Array<ImageView>

    private val gvm: GameViewModel by lazy {
        ViewModelProviders.of(this).get(GameViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findALlViews() // Make all views accessible in the code
        renderDice(gvm.getDice()) // Render the dice to reflect their values

      //  var round = savedInstanceState?.getInt(ROUND) ?: 0
       // var throws = savedInstanceState?.getInt(THROWS) ?: 0
        throwButton.visibility = savedInstanceState?.getInt(THROWVISIBLE) ?: View.VISIBLE
        findViewById<LinearLayout>(R.id.score_buttons).visibility = savedInstanceState?.getInt(
            SCOREMENUVISIBLE) ?: View.GONE



        // Add a listener to the throwButton
        throwButton.setOnClickListener {
            // If the player has thrown less than 2 times
            if (gvm.canThrow()) {
                gvm.throwDice() // Throw the dice
                renderDice(gvm.getDice()) // And update their images accordingly.
            } else {
                gvm.unKeepAllDice()
                renderDice(gvm.getDice())
                throwButton.visibility = View.GONE
                findViewById<LinearLayout>(R.id.score_buttons).visibility = View.VISIBLE
            }
        }
        /**
         * When clicked the next round starts.
         */
        roundDone.setOnClickListener {

        }

        /**
         * Score for the selected dice is calculated and added to the score.
         */
        addScore.setOnClickListener {

        }

        // Add listeners to each die's ImageView.
        for ((index, die) in diceImages.withIndex()) makeDieKeepable(die, index)

    }

    /**
     * Saves the current UI to a bundle.
     */
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(ROUND, gvm.round) // Save the current round (0-10)
        outState.putInt(THROWS, gvm.throws)
        outState.putInt(THROWVISIBLE, throwButton.visibility)
        outState.putInt(SCOREMENUVISIBLE, findViewById<LinearLayout>(R.id.score_buttons).visibility)
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
     * @param isKept Whether the die should be thrown or not.
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
            }
        }
    }

    private fun findALlViews() {
        spinner = findViewById(R.id.spinner) // Dropdown menu for LOW, four, etc
        throwButton = findViewById(R.id.throw_button) // Button for throwing dice.
        addScore = findViewById(R.id.add_score)
        roundDone = findViewById(R.id.done)
        // Fetch all dice from activity_main
        die1 = findViewById(R.id.die1)
        die2 = findViewById(R.id.die2)
        die3 = findViewById(R.id.die3)
        die4 = findViewById(R.id.die4)
        die5 = findViewById(R.id.die5)
        die6 = findViewById(R.id.die6)
        // Save them in an array for easier access
        diceImages = arrayOf(die1, die2, die3, die4, die5, die6)
    }

}
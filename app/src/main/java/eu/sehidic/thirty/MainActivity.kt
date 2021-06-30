package eu.sehidic.thirty

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import eu.sehidic.thirty.model.Choice
import eu.sehidic.thirty.model.Die
import eu.sehidic.thirty.model.GameState
import eu.sehidic.thirty.model.GameViewModel

/**
 * The starting point of the application.
 * @author Delfi Sehidic
 * @version 1.0.0
 */

private const val THROW_VISIBLE = "THROW_VISIBLE"
private const val SCORE_MENU_VISIBLE = "SCORE_MENU_VISIBLE"
private const val SPINNER_ENABLED = "SPINNER_ENABLED"
private const val GAME_STATE = "GAME_STATE"

class MainActivity : AppCompatActivity() {
    private lateinit var spinner: Spinner
    private lateinit var throwButton: Button
    private lateinit var addScore: Button
    private lateinit var roundDone: Button
    private lateinit var scoreButtons: LinearLayout
    private lateinit var diceImages: Array<ImageView>
    private lateinit var gameViewModel: GameViewModel
    private lateinit var choiceAdapter: ArrayAdapter<Choice>
    private lateinit var die1: ImageView
    private lateinit var die2: ImageView
    private lateinit var die3: ImageView
    private lateinit var die4: ImageView
    private lateinit var die5: ImageView
    private lateinit var die6: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // gameViewModel contains most game logic
        gameViewModel = ViewModelProvider(this).get(GameViewModel::class.java)
        findALlViews() // Make all views accessible in the code
        renderDice(gameViewModel.getDice()) // Render the dice to reflect their values

        /**
         * When the throw button is clicked, the dice that have not been
         * selected are thrown. If they've thrown 3 times, the scoring menu is displayed.
         */
        throwButton.setOnClickListener {
            gameViewModel.throwDice() // Throw the dice
            // If the player has thrown less than 2 times
            if (gameViewModel.canThrow()) {
                renderDice(gameViewModel.getDice()) // And update their images accordingly.
            } else {
                // If the player has already thrown, make the dice
                // available for selecting for scoring
                gameViewModel.unKeepAllDice()
                renderDice(gameViewModel.getDice())
                // Show the menu where the user can choose how to score the dice for this round
                showScoreMenu()
            }
        }
        /**
         * When clicked the next round starts or if the game is finished the highscore activity is started.
         */
        roundDone.setOnClickListener {
            // Guard statement to make sure a scoring choice is always selected.
            if (!gameViewModel.hasCurrentChoice()) {
                Toast.makeText(
                    this,
                    "You must select a scoring method.",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                // Display the score from this particular round.
                Toast.makeText(
                    this,
                    "${gameViewModel.getTotalScore()} points this round.",
                    Toast.LENGTH_SHORT
                ).show()
                // Notify gameViewModel that throws should be reset, player's current score
                // should be reset, etc.
                gameViewModel.roundDone()
                // If rounds == 10, the game is over.
                if (gameViewModel.round > 10) { // TODO: Remove magic number 10.
                    // Fetch the rounds
                    val rounds = gameViewModel.getRounds()
                    // And pass them to the HighscoreActivity
                    Intent(this, HighscoreActivity::class.java).also {
                        it.putExtra("EXTRA_ROUNDS", rounds)
                        startActivity(it)
                    }
                    // Reset the game so the user can play again after reviewing the score.
                    gameViewModel.resetGame()
                }
                // Round done, set up for a new round.
                showThrowMenu()
                spinner.isEnabled = true
                // Update the available scoring choices by getting updated data
                val dropDownChoices = gameViewModel.getAvailableChoices().toMutableList()
                choiceAdapter.clear()
                choiceAdapter.addAll(dropDownChoices)
                renderDice(gameViewModel.getDice())
            }
        }

        /**
         * Score for the selected dice is calculated and added to the score.
         */
        addScore.setOnClickListener {
            val chosenDice = gameViewModel.getKeptDice()
            val scoreChoice = spinner.selectedItem
            val score = gameViewModel.addScore(scoreChoice as Choice, chosenDice)
            Toast.makeText(this, "$score points", Toast.LENGTH_SHORT).show()
            renderDice(gameViewModel.getDice())
            spinner.isEnabled = false
        }

        // Add listeners to each die's ImageView.
        for ((index, die) in diceImages.withIndex()) makeDieKeepable(die, index)

    } // END OF onCreate()

    /**
     * Saves the current state to a bundle.
     */
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(THROW_VISIBLE, throwButton.visibility)
        outState.putInt(SCORE_MENU_VISIBLE, scoreButtons.visibility)
        outState.putSerializable(GAME_STATE, gameViewModel.getGameState())
        outState.putBoolean(SPINNER_ENABLED, spinner.isEnabled)
    }

    /**
     * Retrieves the saved state.
     */
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        throwButton.visibility = savedInstanceState.getInt(THROW_VISIBLE)
        scoreButtons.visibility = savedInstanceState.getInt(SCORE_MENU_VISIBLE)
        gameViewModel.setGameState(savedInstanceState.getSerializable(GAME_STATE) as GameState)
        renderDice(gameViewModel.getDice())
        // Make sure the dropdown choices are rendered properly
        val dropDownChoices = gameViewModel.getAvailableChoices().toMutableList()
        choiceAdapter.clear()
        choiceAdapter.addAll(dropDownChoices)
        spinner.isEnabled = savedInstanceState.getBoolean(SPINNER_ENABLED)
    }

    /**
     * Takes an array of dice and updates their image representations.
     * @param dice The dice objects
     */
    private fun renderDice(dice: Array<Die>) {
        for ((index, diceImage) in diceImages.withIndex()) {
            if (dice[index].used) {
                diceImage.visibility = View.INVISIBLE
            } else {
                diceImage.visibility = View.VISIBLE
                diceImage.setImageResource(getDieResource(dice[index].value, dice[index].keep))
            }
        }
    }

    /**
     * Get the drawable resource from a die value.
     * @param dieValue The value (0-6) of the die.
     * @param isKept Whether the die should be thrown or not.
     * @return The resId of the resource.
     */
    private fun getDieResource(dieValue: Int, isKept: Boolean): Int {
        // Values for selected dice
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
        // Values for unselected dice
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

    /**
     * Shows the throw button and hides the scoring buttons.
     */
    private fun showThrowMenu() {
        throwButton.visibility = View.VISIBLE
        scoreButtons.visibility = View.GONE
    }

    /**
     * Shows the scoring buttons and hides the throw button.
     */
    private fun showScoreMenu() {
        throwButton.visibility = View.GONE
        findViewById<LinearLayout>(R.id.score_buttons).visibility = View.VISIBLE
    }

    /**
     * Makes the die selectable for keeping/scoring.
     * Toggles in the model whether their selected or not and triggers a rerender.
     */
    private fun makeDieKeepable(die: ImageView, index: Int) {
        die.setOnClickListener {
            if (gameViewModel.hasThrown()) { // Must've thrown at least once
                gameViewModel.toggleKeep(index)
                renderDice(gameViewModel.getDice())
            }
        }
    }

    /**
     * Basic setup. Finds various views by id and makes them accessible in code.
     */
    private fun findALlViews() {
        spinner = findViewById(R.id.choices) // Dropdown menu for LOW, four, etc
        throwButton = findViewById(R.id.throw_button) // Button for throwing dice.
        addScore = findViewById(R.id.add_score)
        roundDone = findViewById(R.id.done)
        scoreButtons = findViewById(R.id.score_buttons)
        // Fetch all dice from activity_main
        die1 = findViewById(R.id.die1)
        die2 = findViewById(R.id.die2)
        die3 = findViewById(R.id.die3)
        die4 = findViewById(R.id.die4)
        die5 = findViewById(R.id.die5)
        die6 = findViewById(R.id.die6)
        // Save them in an array for easier access
        diceImages = arrayOf(die1, die2, die3, die4, die5, die6)

        // Set up the spinner with choices (LOW, FOUR, FIVE, etc)
        val dropDownChoices = gameViewModel.getAvailableChoices().toMutableList()
        choiceAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, dropDownChoices)
        choiceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
        choiceAdapter.setNotifyOnChange(true)
        spinner.adapter = choiceAdapter
    }
}
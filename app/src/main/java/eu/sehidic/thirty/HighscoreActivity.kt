package eu.sehidic.thirty

import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import eu.sehidic.thirty.model.Round

/**
 * Activity that shows the final score and the score for each round.
 * @author Delfi Sehidic
 */

class HighscoreActivity : AppCompatActivity() {
    private lateinit var container: RecyclerView // Will contain each round
    private lateinit var results: TextView // Displays the total score.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_highscore)

        container = findViewById(R.id.container)
        results =  findViewById(R.id.title_result)

        // Fetch the rounds passed as an intent from MainActivity
        val rounds = intent.getSerializableExtra("EXTRA_ROUNDS") as Array<Round>

        // RoundAdapter is used for inflating each round
        val roundAdapter = RoundAdapter(rounds.asList())
        container.adapter = roundAdapter
        container.layoutManager = LinearLayoutManager(this)
        // Sum the score of each round to calculate the total score
        val sumAllRounds = rounds.fold(0) { acc, e -> acc + e.score }
        results.text = "Total score: $sumAllRounds"

    }

}
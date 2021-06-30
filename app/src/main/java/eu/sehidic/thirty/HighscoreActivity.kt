package eu.sehidic.thirty

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import eu.sehidic.thirty.model.Round

class HighscoreActivity : AppCompatActivity() {
    private lateinit var container: RecyclerView
    private lateinit var results: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_highscore)

        container = findViewById(R.id.container)
        results =  findViewById(R.id.title_result)

        val rounds = intent.getSerializableExtra("EXTRA_ROUNDS") as Array<Round>
        for (round in rounds) Log.d("Highscore", round.toString())

        val roundAdapter = RoundAdapter(rounds.asList())
        container.adapter = roundAdapter
        container.layoutManager = LinearLayoutManager(this)

        val sumAllRounds = rounds.fold(0) { acc, e -> acc + e.score }
        results.text = "Total score: $sumAllRounds"

    }

}
package eu.sehidic.thirty

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Spinner
import androidx.lifecycle.ViewModelProviders
import eu.sehidic.thirty.model.GameViewModel

private const val ROUND = "ROUND"

class MainActivity : AppCompatActivity() {
    private lateinit var spinner: Spinner;

    private val gvm: GameViewModel by lazy {
        ViewModelProviders.of(this).get(GameViewModel::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        spinner = findViewById(R.id.spinner)

        var round = savedInstanceState?.getInt(ROUND) ?: 0

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(ROUND, gvm.round)

    }
}
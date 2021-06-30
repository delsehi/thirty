package eu.sehidic.thirty

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import eu.sehidic.thirty.model.Round

class RoundAdapter(
    private var rounds: List<Round>
) : RecyclerView.Adapter<RoundAdapter.RoundViewHolder>() {

    inner class RoundViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoundViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.round, parent, false)
        return RoundViewHolder(view)
    }

    override fun onBindViewHolder(holder: RoundViewHolder, position: Int) {
        holder.itemView.apply {
            val curr = rounds[position]
            findViewById<TextView>(R.id.tvRound).text =
                "Round ${curr.round} Scoring ${curr.choice} Score ${curr.score}"
        }
    }

    override fun getItemCount(): Int {
        return rounds.size
    }
}
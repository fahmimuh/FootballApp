package com.fahmimuh.kade5.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fahmimuh.kade5.R
import com.fahmimuh.kade5.model.EventsItem
import kotlinx.android.synthetic.main.item_match.view.*
import org.jetbrains.anko.sdk27.coroutines.onClick

class EventsAdapter(
    private val events: List<EventsItem>,
    private val listener: (EventsItem) -> Unit
) : RecyclerView.Adapter<NextMatchViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NextMatchViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_match, parent, false)
        return NextMatchViewHolder(view)
    }

    override fun onBindViewHolder(holder: NextMatchViewHolder, position: Int) {
        holder.bindItem(events[position], listener)
    }

    override fun getItemCount(): Int = events.size

}

class NextMatchViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bindItem(event: EventsItem, listener: (EventsItem) -> Unit) {
        if (event.strHomeTeam != null) {
            itemView.tvDate.text = event.dateEvent
            itemView.tvHome.text = event.strHomeTeam
            itemView.tvAway.text = event.strAwayTeam
            if (event.intHomeScore != null) {
                itemView.tvHomeScore?.text = event.intHomeScore.toString()
                itemView.tvAwayScore?.text = event.intAwayScore.toString()
            } else {
                itemView.tvHomeScore?.text = "-"
                itemView.tvAwayScore?.text = "-"
            }
        }
        itemView.onClick { listener(event) }
    }

}

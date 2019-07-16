package com.fahmimuh.kade5.main.favorites.match

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fahmimuh.kade5.R
import com.fahmimuh.kade5.db.FavoriteEvents
import kotlinx.android.synthetic.main.item_match.view.*
import org.jetbrains.anko.sdk27.coroutines.onClick

class FavoriteEventsAdapter(
    private val favoriteEvent: List<FavoriteEvents>,
    private val listener: (FavoriteEvents) -> Unit
) : RecyclerView.Adapter<FavoriteEventViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteEventViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_match, parent, false)
        return FavoriteEventViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteEventViewHolder, position: Int) {
        holder.bindItem(favoriteEvent[position], listener)
    }

    override fun getItemCount(): Int = favoriteEvent.size

}

class FavoriteEventViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    fun bindItem(events: FavoriteEvents, listener: (FavoriteEvents) -> Unit) {
        itemView.tvDate.text = events.dateEvent
        itemView.tvHome.text = events.strHomeTeam
        itemView.tvAway.text = events.strAwayTeam
        if (events.intHomeScore != null) {
            itemView.tvHomeScore?.text = events.intHomeScore.toString()
            itemView.tvAwayScore?.text = events.intAwayScore.toString()
        } else {
            itemView.tvHomeScore?.text = "-"
            itemView.tvAwayScore?.text = "-"

        }

        itemView.onClick { listener(events) }
    }
}

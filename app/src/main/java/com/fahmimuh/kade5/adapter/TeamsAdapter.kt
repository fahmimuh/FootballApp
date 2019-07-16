package com.fahmimuh.kade5.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fahmimuh.kade5.R
import com.fahmimuh.kade5.model.TeamsItem
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_team.view.*
import org.jetbrains.anko.sdk27.coroutines.onClick

class TeamsAdapter(
    private val teams: List<TeamsItem>,
    private val listener: (TeamsItem) -> Unit
) : RecyclerView.Adapter<TeamsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamsViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_team, parent, false)
        return TeamsViewHolder(view)
    }

    override fun onBindViewHolder(holder: TeamsViewHolder, position: Int) {
        holder.bindItem(teams[position], listener)
    }

    override fun getItemCount(): Int = teams.size

}


class TeamsViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bindItem(teams: TeamsItem, listener: (TeamsItem) -> Unit) {
        Picasso.get().load(teams.strTeamBadge).into(itemView.imgTeamBadge)
        itemView.tvTeamName.text = teams.strTeam

        itemView.onClick { listener(teams) }
    }
}
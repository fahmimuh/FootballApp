package com.fahmimuh.kade5.main.favorites.team

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fahmimuh.kade5.R
import com.fahmimuh.kade5.db.FavoriteTeams
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_team.view.*
import org.jetbrains.anko.sdk27.coroutines.onClick

class FavoriteTeamsAdapter(
    private val favoriteTeams: List<FavoriteTeams>,
    private val listener: (FavoriteTeams) -> Unit
) : RecyclerView.Adapter<FavoriteTeamsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteTeamsViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_team, parent, false)
        return FavoriteTeamsViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteTeamsViewHolder, position: Int) {
        holder.bindItem(favoriteTeams[position], listener)
    }

    override fun getItemCount(): Int = favoriteTeams.size

}

class FavoriteTeamsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    fun bindItem(teams: FavoriteTeams, listener: (FavoriteTeams) -> Unit) {
        Picasso.get().load(teams.teamBadge).into(itemView.imgTeamBadge)
        itemView.tvTeamName.text = teams.teamName

        itemView.onClick { listener(teams) }
    }
}

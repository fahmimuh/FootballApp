package com.fahmimuh.kade5.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fahmimuh.kade5.R
import com.fahmimuh.kade5.model.PlayersItem
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_player.view.*
import org.jetbrains.anko.sdk27.coroutines.onClick

class PlayersAdapter(
    private val players: List<PlayersItem>,
    private val listener: (PlayersItem) -> Unit
) : RecyclerView.Adapter<PlayersViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayersViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_player, parent, false)
        return PlayersViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlayersViewHolder, position: Int) {
        holder.bindItem(players[position], listener)
    }

    override fun getItemCount(): Int = players.size

}


class PlayersViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bindItem(player: PlayersItem, listener: (PlayersItem) -> Unit) {
        Picasso.get().load(player.strCutout).into(itemView.imgPlayerBadge)
        itemView.tvPlayerName.text = player.strPlayer
        itemView.tvPlayerPos.text = player.strPosition

        itemView.onClick { listener(player) }
    }
}
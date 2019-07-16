package com.fahmimuh.kade5.detail.detailteam.players

import com.fahmimuh.kade5.model.PlayersItem

interface PlayersView {
    fun showPlayerList(data: List<PlayersItem>?)
}
package com.fahmimuh.kade5.model

import com.google.gson.annotations.SerializedName


data class PlayersResponse(

    @SerializedName("player")
    var player: List<PlayersItem>? = null


)
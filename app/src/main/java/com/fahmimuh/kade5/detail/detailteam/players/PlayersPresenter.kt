package com.fahmimuh.kade5.detail.detailteam.players

import com.fahmimuh.kade5.api.ApiRepository
import com.fahmimuh.kade5.api.TheSportDBApi
import com.fahmimuh.kade5.model.PlayersResponse
import com.fahmimuh.kade5.util.CoroutineContextProvider
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PlayersPresenter(private val view: PlayersView,
                       private val apiRepository: ApiRepository,
                       private val gson: Gson,
                       private val contextPool: CoroutineContextProvider = CoroutineContextProvider()
) {

    fun getTeamsList(teamId:String?) {
        GlobalScope.launch(contextPool.main){
            val data = gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getPlayerList(teamId)).await(),
                PlayersResponse::class.java
            )
            view.showPlayerList(data.player)
        }
    }

}
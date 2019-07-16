package com.fahmimuh.kade5.detail.detailteam

import com.fahmimuh.kade5.api.ApiRepository
import com.fahmimuh.kade5.api.TheSportDBApi
import com.fahmimuh.kade5.model.TeamsResponse
import com.fahmimuh.kade5.util.CoroutineContextProvider
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DetailTeamPresenter(
    private val matchView: DetailTeamView,
    private val apiRepository: ApiRepository,
    private val gson: Gson,
    private val contextPool: CoroutineContextProvider = CoroutineContextProvider()
) {
        fun getTeamData(teamId: String?) {
        GlobalScope.launch(contextPool.main) {
            val data = gson.fromJson(
                apiRepository
                    .doRequest(TheSportDBApi.getTeam(teamId)).await(),
                TeamsResponse::class.java
            )
            matchView.showTeamData(data.teams)
        }
    }
}
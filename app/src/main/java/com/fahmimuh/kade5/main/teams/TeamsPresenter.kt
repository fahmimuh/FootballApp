package com.fahmimuh.kade5.main.teams

import com.fahmimuh.kade5.api.ApiRepository
import com.fahmimuh.kade5.api.TheSportDBApi
import com.fahmimuh.kade5.model.TeamsResponse
import com.fahmimuh.kade5.util.CoroutineContextProvider
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TeamsPresenter(private val view: TeamsView,
                     private val apiRepository: ApiRepository,
                     private val gson: Gson,
                     private val contextPool: CoroutineContextProvider = CoroutineContextProvider()
) {

    fun getTeamsList(leagueId:String) {
        GlobalScope.launch(contextPool.main){
            val data = gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getTeamList(leagueId)).await(),
                TeamsResponse::class.java
            )
            view.showTeamList(data.teams)
        }
    }

    fun getTeamsSearch(name:String?) {
        GlobalScope.launch(contextPool.main){
            val data = gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.searchTeam(name)).await(),
                TeamsResponse::class.java
            )
            view.showTeamList(data.teams)
        }
    }

}
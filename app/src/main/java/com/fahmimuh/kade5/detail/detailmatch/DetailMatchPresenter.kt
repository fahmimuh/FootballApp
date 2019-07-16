package com.fahmimuh.kade5.detail.detailmatch

import com.fahmimuh.kade5.api.ApiRepository
import com.fahmimuh.kade5.api.TheSportDBApi
import com.fahmimuh.kade5.model.EventsResponse
import com.fahmimuh.kade5.model.TeamsResponse
import com.fahmimuh.kade5.util.CoroutineContextProvider
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DetailMatchPresenter(
    private val matchView: DetailMatchView,
    private val apiRepository: ApiRepository,
    private val gson: Gson,
    private val contextPool: CoroutineContextProvider = CoroutineContextProvider()
) {

    fun getEventData(eventId: String) {
        GlobalScope.launch(contextPool.main) {
            val data = gson.fromJson(
                apiRepository
                    .doRequest(TheSportDBApi.getEventData(eventId)).await(),
                EventsResponse::class.java
            )
            matchView.showEventData(data.events?.get(0))

        }
    }

    fun getTeamBadge(idHome: String?, idAway: String?) {
        GlobalScope.launch(contextPool.main) {
            val homeData = gson.fromJson(
                apiRepository
                    .doRequest(TheSportDBApi.getTeam(idHome)).await(),
                TeamsResponse::class.java
            )
            val awayData = gson.fromJson(
                apiRepository
                    .doRequest(TheSportDBApi.getTeam(idAway)).await(),
                TeamsResponse::class.java
            )
            matchView.showTeamBadge(homeData.teams, awayData.teams)
        }
    }
}
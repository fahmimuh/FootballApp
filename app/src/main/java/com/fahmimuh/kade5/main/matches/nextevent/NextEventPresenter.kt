package com.fahmimuh.kade5.main.matches.nextevent

import com.fahmimuh.kade5.api.ApiRepository
import com.fahmimuh.kade5.api.TheSportDBApi
import com.fahmimuh.kade5.model.EventSearchResponse
import com.fahmimuh.kade5.model.EventsResponse
import com.fahmimuh.kade5.util.CoroutineContextProvider
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class NextEventPresenter(private val view: NextEventView,
                         private val apiRepository: ApiRepository,
                         private val gson: Gson,
                         private val contextPool: CoroutineContextProvider = CoroutineContextProvider()
) {

    fun getNextEventList(leagueId:Int?) {
        GlobalScope.launch(contextPool.main){
            val data = gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getNextEventa(leagueId)).await(),
                EventsResponse::class.java
            )
            view.showNextEventList(data.events)
        }
    }

    fun getTeamsSearch(name:String?) {
        GlobalScope.launch(contextPool.main){
            val data = gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.searchEvent(name)).await(),
                EventSearchResponse::class.java
            )
            view.showNextEventList(data.event)
        }
    }

}
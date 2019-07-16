package com.fahmimuh.kade5.detail


import com.fahmimuh.kade5.TestContextProvider
import com.fahmimuh.kade5.api.ApiRepository
import com.fahmimuh.kade5.api.TheSportDBApi
import com.fahmimuh.kade5.detail.detailmatch.DetailMatchPresenter
import com.fahmimuh.kade5.detail.detailmatch.DetailMatchView
import com.fahmimuh.kade5.model.EventsItem
import com.fahmimuh.kade5.model.EventsResponse
import com.fahmimuh.kade5.model.TeamsItem
import com.fahmimuh.kade5.model.TeamsResponse
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations


class DetailMatchPresenterTest {
    @Mock
    private
    lateinit var view: DetailMatchView

    @Mock
    private
    lateinit var gson: Gson

    @Mock
    private
    lateinit var apiRepository: ApiRepository

    private lateinit var presenter: DetailMatchPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = DetailMatchPresenter(view, apiRepository, gson, TestContextProvider())
    }

    @Test
    fun testGetTeamDetail()  {
        val events: MutableList<EventsItem> = mutableListOf()
        val response = EventsResponse(events)
        val eventId = "441613"

        GlobalScope.launch {
            Mockito.`when`(gson.fromJson(apiRepository
                    .doRequest(TheSportDBApi.getEventData(eventId)).await(),
                    EventsResponse::class.java
            )).thenReturn(response)

            presenter.getEventData(eventId)

            Mockito.verify(view).showEventData(events[0])
        }
    }

    @Test
    fun testGetTeamBadge()  {
        val teams: MutableList<TeamsItem> = mutableListOf()
        val response = TeamsResponse(teams)
        val idHome = "133604"
        val idAway = "133605"

        GlobalScope.launch {
            Mockito.`when`(gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getTeam(idHome)).await(),
                TeamsResponse::class.java
            )).thenReturn(response)

            Mockito.`when`(gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getTeam(idAway)).await(),
                TeamsResponse::class.java
            )).thenReturn(response)

            presenter.getTeamBadge(idHome,idAway)

            Mockito.verify(view).showTeamBadge(teams,teams)
        }
    }

}
package com.fahmimuh.kade5.detail.detailmatch

import com.fahmimuh.kade5.model.EventsItem
import com.fahmimuh.kade5.model.TeamsItem

interface DetailMatchView {
    fun showEventData(event: EventsItem?)
    fun showTeamBadge(home: List<TeamsItem>?, away: List<TeamsItem>?)
}

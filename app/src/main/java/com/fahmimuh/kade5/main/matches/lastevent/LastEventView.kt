package com.fahmimuh.kade5.main.matches.lastevent

import com.fahmimuh.kade5.model.EventsItem

interface LastEventView {
    fun showLastEventList(data: List<EventsItem>?)
}

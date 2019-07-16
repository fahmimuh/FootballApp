package com.fahmimuh.kade5.main.matches.nextevent

import com.fahmimuh.kade5.model.EventsItem

interface NextEventView {
    fun showNextEventList(data: List<EventsItem>?)
}

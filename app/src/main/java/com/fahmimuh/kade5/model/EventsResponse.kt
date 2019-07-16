package com.fahmimuh.kade5.model

import com.google.gson.annotations.SerializedName

data class EventsResponse(

	@SerializedName("events")
	var events: List<EventsItem>? = null
)
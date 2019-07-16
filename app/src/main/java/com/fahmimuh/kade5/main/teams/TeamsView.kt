package com.fahmimuh.kade5.main.teams

import com.fahmimuh.kade5.model.TeamsItem

interface TeamsView {
    fun showTeamList(data:List<TeamsItem>?)
}
package com.fahmimuh.kade5.db

data class FavoriteEvents(val id:Long?, val eventId:String?, val dateEvent:String?,
                          val strHomeTeam:String?, val strAwayTeam:String?,
                          val intHomeScore:String?, val intAwayScore:String?) {

    companion object {
        const val TABLE_EVENT_FAVORITE = "table_event_favorite"
        const val ID = "ID_"
        const val EVENT_ID = "event_id"
        const val EVENT_DATE = "event_date"
        const val HOME_TEAM = "home_team"
        const val AWAY_TEAM = "away_team"
        const val HOME_SCORE = "home_score"
        const val AWAY_SCORE = "away_score"
    }
}
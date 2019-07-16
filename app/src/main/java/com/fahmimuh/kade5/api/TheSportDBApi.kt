package com.fahmimuh.kade5.api

import com.fahmimuh.kade5.BuildConfig
import com.fahmimuh.kade5.BuildConfig.TSDB_API_KEY


object TheSportDBApi {

    fun getLastEventa(leagueId:Int?) : String{
        return BuildConfig.BASE_URL + "api/v1/json/$TSDB_API_KEY"+"/eventspastleague.php?id="+leagueId
    }

    fun getNextEventa(leagueId:Int?) : String{
        return BuildConfig.BASE_URL + "api/v1/json/$TSDB_API_KEY"+"/eventsnextleague.php?id="+leagueId
    }

    fun getEventData(eventId:String?) : String{
        return BuildConfig.BASE_URL + "api/v1/json/$TSDB_API_KEY"+"/lookupevent.php?id="+eventId
    }

    fun getTeam(idTeam:String?) : String{
        return BuildConfig.BASE_URL + "api/v1/json/$TSDB_API_KEY"+"/lookupteam.php?id="+idTeam
    }

    fun getTeamList(league: String?):String{
        return BuildConfig.BASE_URL + "api/v1/json/$TSDB_API_KEY"+"/search_all_teams.php?l="+league
    }

    fun getPlayerList(teamId: String?):String{
        return BuildConfig.BASE_URL + "api/v1/json/$TSDB_API_KEY"+"/lookup_all_players.php?id="+teamId
    }

    fun searchTeam(name:String?):String{
        return BuildConfig.BASE_URL + "api/v1/json/$TSDB_API_KEY"+"/searchteams.php?t="+name
    }

    fun searchEvent(name:String?):String{
        return BuildConfig.BASE_URL + "api/v1/json/$TSDB_API_KEY"+"/searchevents.php?e="+name
    }


}




package com.fahmimuh.kade5.detail.detailmatch

import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.fahmimuh.kade5.api.ApiRepository
import com.fahmimuh.kade5.db.FavoriteEvents
import com.fahmimuh.kade5.db.databaseEvent
import com.fahmimuh.kade5.model.EventsItem
import com.fahmimuh.kade5.model.TeamsItem
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import com.fahmimuh.kade5.R
import kotlinx.android.synthetic.main.activity_detail_match.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.toast

class DetailMatchMatchActivity : AppCompatActivity(), DetailMatchView {
    private lateinit var matchPresenter: DetailMatchPresenter
    private lateinit var eventId: String

    private var events: EventsItem? = null
    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_match)
        this.supportActionBar?.title = "Match Detail"
        this.supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val request = ApiRepository()
        val gson = Gson()

        eventId = intent.getStringExtra("eventId")

        matchPresenter = DetailMatchPresenter(this, request, gson)
        matchPresenter.getEventData(eventId)

        favoriteState()
}

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        menuItem = menu
        setFavorite()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            R.id.add_to_favorite -> {
                if (events != null) {
                    if (isFavorite) removeFromFavorite() else addToFavorite()

                    isFavorite = !isFavorite
                    setFavorite()
                } else toast("Data masih null")
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun favoriteState() {
        databaseEvent.use {
            val result = select(FavoriteEvents.TABLE_EVENT_FAVORITE)
                .whereArgs("(EVENT_ID = {id})", "id" to eventId)
            val favorite = result.parseList(classParser<FavoriteEvents>())
            if (!favorite.isEmpty()) isFavorite = true
            setFavorite()
        }
    }

    override fun showEventData(event: EventsItem?) {
        events = event

        val idHome = event?.idHomeTeam
        val idAway = event?.idAwayTeam
        tvDateEvent.text = event?.dateEvent
        tvHomeTeam.text = event?.strHomeTeam
        tvAwayTeam.text = event?.strAwayTeam
        if (event?.intHomeScore != null) {
            tvHomeScore.text = event.intHomeScore.toString()
            tvAwayScore.text = event.intAwayScore.toString()
            tvGoalHomeDetails.text = event.strHomeGoalDetails.toString()
            tvGoalAwayDetails.text = event.strAwayGoalDetails.toString()
        } else {
            tvHomeScore.text = "-"
            tvAwayScore.text = "-"
        }
        matchPresenter.getTeamBadge(idHome, idAway)
    }

    override fun showTeamBadge(home: List<TeamsItem>?, away: List<TeamsItem>?) {
        val homeBadge = home?.get(0)?.strTeamBadge
        val awayBadge = away?.get(0)?.strTeamBadge

        Picasso.get().load(homeBadge).into(imgHomeBadge)
        Picasso.get().load(awayBadge).into(imgAwayBadge)
        tvStadium.text = home?.get(0)?.strStadium
    }


    private fun addToFavorite() {

        try {
            databaseEvent.use {
                insert(
                    FavoriteEvents.TABLE_EVENT_FAVORITE,
                    FavoriteEvents.EVENT_ID to events?.idEvent,
                    FavoriteEvents.EVENT_DATE to events?.dateEvent,
                    FavoriteEvents.HOME_TEAM to events?.strHomeTeam,
                    FavoriteEvents.AWAY_TEAM to events?.strAwayTeam,
                    FavoriteEvents.HOME_SCORE to events?.intHomeScore,
                    FavoriteEvents.AWAY_SCORE to events?.intAwayScore
                )
            }
            toast("Added to favorite")
        } catch (e: SQLiteConstraintException) {
            toast(e.localizedMessage)
        }

    }

    private fun removeFromFavorite() {
        try {
            databaseEvent.use {
                delete(
                        FavoriteEvents.TABLE_EVENT_FAVORITE, "(EVENT_ID = {id})",
                "id" to eventId
                )
            }
            toast("Removed from favorite")
        } catch (e: SQLiteConstraintException) {
            toast(e.localizedMessage)
        }
    }

    private fun setFavorite() {
        if (isFavorite)
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_added_to_favorites)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_add_to_favorites)
    }


}

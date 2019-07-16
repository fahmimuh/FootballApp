package com.fahmimuh.kade5.detail.detailteam

import android.database.sqlite.SQLiteException
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.fahmimuh.kade5.R
import com.fahmimuh.kade5.api.ApiRepository
import com.fahmimuh.kade5.db.FavoriteTeams
import com.fahmimuh.kade5.db.database
import com.fahmimuh.kade5.model.TeamsItem
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_team.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.toast

class DetailTeamActivity : AppCompatActivity(), DetailTeamView {
    private lateinit var presenter: DetailTeamPresenter
    private lateinit var teamId: String

    private var menuItem: Menu? = null
    private var teams: TeamsItem? = null
    private var isFavorite: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_team)
        supportActionBar?.title = "Team Detail"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val request = ApiRepository()
        val gson = Gson()
        val viewPager = findViewById<ViewPager>(R.id.vpTeam)
        val tabs = findViewById<TabLayout>(R.id.tabs_team)

        teamId = intent.getStringExtra("teamId")

        presenter = DetailTeamPresenter(this, request, gson)
        presenter.getTeamData(teamId)

        viewPager.adapter = DetailPagerAdapter(supportFragmentManager)
        tabs.setupWithViewPager(viewPager)

        favoriteState()
    }

    override fun showTeamData(data: List<TeamsItem>?) {
        teams = data?.get(0)

        Picasso.get().load(data?.get(0)?.strTeamBadge).into(imgTeamBadge)
        tvTeamName.text = data?.get(0)?.strTeam
        tvTeamStadium.text = data?.get(0)?.strStadium
        tvTeamYear.text = data?.get(0)?.intFormedYear
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
                if (teams != null) {
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
        database.use {
            val result = select(FavoriteTeams.TABLE_TEAM_FAVORITE)
                .whereArgs("(TEAM_ID = {id})", "id" to teamId)
            val team = result.parseList(classParser<FavoriteTeams>())
            if (!team.isEmpty()) isFavorite = true
            setFavorite()
        }
    }

    private fun addToFavorite() {

        try {
            database.use {
                insert(
                    FavoriteTeams.TABLE_TEAM_FAVORITE,
                    FavoriteTeams.TEAM_ID to teams?.idTeam,
                    FavoriteTeams.TEAM_NAME to teams?.strTeam,
                    FavoriteTeams.TEAM_BADGE to teams?.strTeamBadge
                )

            }

            toast("Added to favorite")
        } catch (e: SQLiteException) {

            toast(e.localizedMessage)
        }
    }

    private fun removeFromFavorite() {
        try {
            database.use {
                delete(
                    FavoriteTeams.TABLE_TEAM_FAVORITE,
                    "(TEAM_ID = {id})", "id" to teamId
                )
            }
            toast("Removed from favorite")

        } catch (e: SQLiteException) {
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

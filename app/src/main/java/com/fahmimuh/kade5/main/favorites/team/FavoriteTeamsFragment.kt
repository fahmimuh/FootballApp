package com.fahmimuh.kade5.main.favorites.team


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fahmimuh.kade5.R
import com.fahmimuh.kade5.db.FavoriteTeams
import com.fahmimuh.kade5.db.database
import com.fahmimuh.kade5.detail.detailteam.DetailTeamActivity
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.support.v4.startActivity


class FavoriteTeamsFragment : Fragment() {
    private var favoriteTeams: MutableList<FavoriteTeams> = mutableListOf()
    private lateinit var adapter: FavoriteTeamsAdapter
    private lateinit var favorite: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_favorite_teams, container, false)

        favorite = view.findViewById(R.id.rvFavTeam)
        favorite.layoutManager = LinearLayoutManager(view.context)

        adapter = FavoriteTeamsAdapter(favoriteTeams) {
                        startActivity<DetailTeamActivity>("teamId" to "${it.teamId}")
        }

        favorite.adapter = adapter
        showFavorite()

        return view
    }

    private fun showFavorite() {
        activity?.database?.use {
            val result = select(FavoriteTeams.TABLE_TEAM_FAVORITE)
            val favorite = result.parseList(classParser<FavoriteTeams>())

            favoriteTeams.addAll(favorite)
            adapter.notifyDataSetChanged()
        }

    }
}

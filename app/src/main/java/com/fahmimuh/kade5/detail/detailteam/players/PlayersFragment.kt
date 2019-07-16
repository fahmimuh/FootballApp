package com.fahmimuh.kade5.detail.detailteam.players


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fahmimuh.kade5.R
import com.fahmimuh.kade5.adapter.PlayersAdapter
import com.fahmimuh.kade5.api.ApiRepository
import com.fahmimuh.kade5.detail.detailplayer.DetailPlayerActivity
import com.fahmimuh.kade5.model.PlayersItem
import com.google.gson.Gson
import org.jetbrains.anko.support.v4.startActivity

class PlayersFragment : Fragment(), PlayersView {
    private var players: MutableList<PlayersItem> = mutableListOf()
    private lateinit var presenter: PlayersPresenter
    private lateinit var adapter: PlayersAdapter
    private lateinit var playerlist: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val request = ApiRepository()
        val gson = Gson()
        val view = inflater.inflate(R.layout.fragment_players, container, false)
        val teamId = activity?.intent?.getStringExtra("teamId")

        adapter = PlayersAdapter(players) {
            startActivity<DetailPlayerActivity>("thumb" to "${it.strThumb}",
                                                "name" to "${it.strPlayer}",
                                                "club" to "${it.strTeam}",
                                                "pos" to "${it.strPosition}",
                                                "desc" to "${it.strDescriptionEN}")
        }

        presenter = PlayersPresenter(this, request, gson)
        presenter.getTeamsList(teamId)

        playerlist = view.findViewById(R.id.rvPlayers)
        playerlist.layoutManager = LinearLayoutManager(activity)
        playerlist.adapter = adapter

        return view
    }

    override fun showPlayerList(data: List<PlayersItem>?) {
        players.clear()
        if (data != null) {
            players.addAll(data)
        }
        adapter.notifyDataSetChanged()
    }

}

package com.fahmimuh.kade5.detail.detailteam.overview


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fahmimuh.kade5.R
import com.fahmimuh.kade5.api.ApiRepository
import com.fahmimuh.kade5.detail.detailteam.DetailTeamPresenter
import com.fahmimuh.kade5.detail.detailteam.DetailTeamView
import com.fahmimuh.kade5.model.TeamsItem
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_overview.*

class OverviewFragment : Fragment(), DetailTeamView {
    private lateinit var presenter: DetailTeamPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val request = ApiRepository()
        val gson = Gson()
        val view = inflater.inflate(R.layout.fragment_overview, container, false)
        val teamId = activity?.intent?.getStringExtra("teamId")

        presenter = DetailTeamPresenter(this, request, gson)
        presenter.getTeamData(teamId)

        return view
    }

    override fun showTeamData(data: List<TeamsItem>?) {
        tvOverview.text = data?.get(0)?.strDescriptionEN
    }
}

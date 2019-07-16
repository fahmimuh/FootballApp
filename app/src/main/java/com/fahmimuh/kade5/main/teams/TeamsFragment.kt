package com.fahmimuh.kade5.main.teams


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.text.TextUtils
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.fahmimuh.kade5.R
import com.fahmimuh.kade5.adapter.TeamsAdapter
import com.fahmimuh.kade5.api.ApiRepository
import com.fahmimuh.kade5.detail.detailteam.DetailTeamActivity
import com.fahmimuh.kade5.model.TeamsItem
import com.fahmimuh.kade5.util.invisible
import com.fahmimuh.kade5.util.visible
import com.google.gson.Gson
import org.jetbrains.anko.find
import org.jetbrains.anko.support.v4.startActivity

class TeamsFragment : Fragment(), TeamsView {
    private var teams: MutableList<TeamsItem> = mutableListOf()
    private var leagueId: String = ""
    private lateinit var presenter: TeamsPresenter
    private lateinit var adapter: TeamsAdapter
    private lateinit var teamslist: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var spinnerTeams: Spinner

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val request = ApiRepository()
        val gson = Gson()
        val view = inflater.inflate(R.layout.fragment_teams, container, false)
        val spinnerItems = resources.getStringArray(R.array.league)
        val spinnerAdapter = ArrayAdapter(
            context,
            android.R.layout.simple_spinner_dropdown_item, spinnerItems
        )
        spinnerTeams = view.find(R.id.spinnerTeam)
        spinnerTeams.adapter = spinnerAdapter

        setHasOptionsMenu(true)

        adapter = TeamsAdapter(teams) {
            startActivity<DetailTeamActivity>("teamId" to "${it.idTeam}")
        }

        presenter = TeamsPresenter(this, request, gson)

        spinnerTeams.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                leagueId = spinnerTeams.selectedItem.toString()
                presenter.getTeamsList(leagueId)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        teamslist = view.findViewById(R.id.rvTeam)
        teamslist.layoutManager = LinearLayoutManager(activity)
        teamslist.adapter = adapter

        return view
    }

    override fun showTeamList(data: List<TeamsItem>?) {
        teams.clear()
        if (data != null) {
            teams.addAll(data)
        }
        adapter.notifyDataSetChanged()
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.main_menu, menu)

        val searchItem : MenuItem? = menu?.findItem(R.id.action_search)


        searchView = searchItem?.actionView as SearchView
        searchView.queryHint = "Search Team"
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                if(TextUtils.isEmpty(newText)){
                    presenter.getTeamsList(leagueId)
                    spinnerTeams.visible()
                }else{
                    presenter.getTeamsSearch(newText)
                    spinnerTeams.invisible()
                }
                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

        })
    }


}

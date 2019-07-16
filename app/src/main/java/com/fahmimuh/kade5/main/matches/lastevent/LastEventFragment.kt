package com.fahmimuh.kade5.main.matches.lastevent

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
import com.fahmimuh.kade5.R.array.league
import com.fahmimuh.kade5.adapter.EventsAdapter
import com.fahmimuh.kade5.api.ApiRepository
import com.fahmimuh.kade5.detail.detailmatch.DetailMatchMatchActivity
import com.fahmimuh.kade5.model.EventsItem
import com.fahmimuh.kade5.model.TeamsItem
import com.fahmimuh.kade5.main.teams.TeamsPresenter
import com.fahmimuh.kade5.main.teams.TeamsView
import com.fahmimuh.kade5.util.invisible
import com.fahmimuh.kade5.util.visible
import com.google.gson.Gson
import org.jetbrains.anko.find
import org.jetbrains.anko.support.v4.startActivity

class LastEventFragment : Fragment(), LastEventView, TeamsView {
    private var events: MutableList<EventsItem> = mutableListOf()
    private var idLeague: Int? = null
    private lateinit var presenter: LastEventPresenter
    private lateinit var teamsPresenter: TeamsPresenter
    private lateinit var adapter: EventsAdapter
    private lateinit var lastEvent: RecyclerView
    private lateinit var leagueName: String
    private lateinit var searchView: SearchView
    private lateinit var spinnerLast: Spinner

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val request = ApiRepository()
        val gson = Gson()
        val view = inflater.inflate(R.layout.fragment_last_match, container, false)
        val spinnerItems = resources.getStringArray(league)
        val spinnerAdapter = ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, spinnerItems)
        spinnerLast = view.find(R.id.spinnerLast)
        spinnerLast.adapter = spinnerAdapter

        setHasOptionsMenu(true)

        adapter = EventsAdapter(events) {
            startActivity<DetailMatchMatchActivity>("eventId" to "${it.idEvent}")
        }

        presenter = LastEventPresenter(this, request, gson)
        teamsPresenter = TeamsPresenter(this, request, gson)

        spinnerLast.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                leagueName = spinnerLast.selectedItem.toString()
                teamsPresenter.getTeamsList(leagueName)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        lastEvent = view.findViewById(R.id.rvLast)
        lastEvent.layoutManager = LinearLayoutManager(activity)
        lastEvent.adapter = adapter


        return view
    }

    override fun showLastEventList(data: List<EventsItem>?) {
        events.clear()
        if (data != null) {
            events.addAll(data)
        }
        adapter.notifyDataSetChanged()
    }

    override fun showTeamList(data: List<TeamsItem>?) {
        if (data != null) {
            idLeague = data[0].idLeague?.toInt()
            idLeague?.let { presenter.getLastEventList(it) }
        }
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
                    presenter.getLastEventList(idLeague)
                    spinnerLast.visible()
                }else{
                    presenter.getTeamsSearch(newText)
                    spinnerLast.invisible()
                }
                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

        })
    }

}

package com.fahmimuh.kade5.main.matches.nextevent

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
import com.fahmimuh.kade5.adapter.EventsAdapter
import com.fahmimuh.kade5.api.ApiRepository
import com.fahmimuh.kade5.detail.detailmatch.DetailMatchMatchActivity
import com.fahmimuh.kade5.main.teams.TeamsPresenter
import com.fahmimuh.kade5.main.teams.TeamsView
import com.fahmimuh.kade5.model.EventsItem
import com.fahmimuh.kade5.model.TeamsItem
import com.fahmimuh.kade5.util.invisible
import com.fahmimuh.kade5.util.visible
import com.google.gson.Gson
import org.jetbrains.anko.find
import org.jetbrains.anko.support.v4.startActivity

class NextEventFragment : Fragment(), NextEventView, TeamsView {
    private var events: MutableList<EventsItem> = mutableListOf()
    private var idLeague: Int? = null
    private lateinit var presenter: NextEventPresenter
    private lateinit var teamsPresenter: TeamsPresenter
    private lateinit var adapter: EventsAdapter
    private lateinit var nextEvent: RecyclerView
    private lateinit var leagueName: String
    private lateinit var searchView: SearchView
    private lateinit var spinnerNext: Spinner

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val request = ApiRepository()
        val gson = Gson()
        val view = inflater.inflate(R.layout.fragment_next_match, container, false)
        val spinnerItems = resources.getStringArray(R.array.league)
        val spinnerAdapter = ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, spinnerItems)
        spinnerNext = view.find(R.id.spinnerNext)
        spinnerNext.adapter = spinnerAdapter

        setHasOptionsMenu(true)

        adapter = EventsAdapter(events) {
            startActivity<DetailMatchMatchActivity>("eventId" to "${it.idEvent}")
        }

        presenter = NextEventPresenter(this, request, gson)
        teamsPresenter = TeamsPresenter(this, request, gson)


        spinnerNext.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                leagueName = spinnerNext.selectedItem.toString()
                teamsPresenter.getTeamsList(leagueName)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        nextEvent = view.findViewById(R.id.rvNext)
        nextEvent.layoutManager = LinearLayoutManager(activity)
        nextEvent.adapter = adapter


        return view
    }

    override fun showNextEventList(data: List<EventsItem>?) {
        events.clear()
        if (data != null) {
            events.addAll(data)
        }
        adapter.notifyDataSetChanged()
    }

    override fun showTeamList(data: List<TeamsItem>?) {
        if (data != null) {
            idLeague = data[0].idLeague?.toInt()
            idLeague?.let { presenter.getNextEventList(it) }
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
                    presenter.getNextEventList(idLeague)
                    spinnerNext.visible()
                }else{
                    presenter.getTeamsSearch(newText)
                    spinnerNext.invisible()
                }
                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

        })
    }

}

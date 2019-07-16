package com.fahmimuh.kade5.main.favorites.match


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fahmimuh.kade5.R
import com.fahmimuh.kade5.db.FavoriteEvents
import com.fahmimuh.kade5.db.databaseEvent
import com.fahmimuh.kade5.detail.detailmatch.DetailMatchMatchActivity
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.support.v4.startActivity


class FavoriteEventsFragment : Fragment() {
    private var favoriteEvents: MutableList<FavoriteEvents> = mutableListOf()
    private lateinit var adapter: FavoriteEventsAdapter
    private lateinit var favorite: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_favorite_events, container, false)

        favorite = view.findViewById(R.id.rvFav)
        favorite.layoutManager = LinearLayoutManager(view.context)

        adapter = FavoriteEventsAdapter(favoriteEvents) {
                        startActivity<DetailMatchMatchActivity>("eventId" to "${it.eventId}")
        }

        favorite.adapter = adapter
        showFavorite()

        return view
    }

    private fun showFavorite() {
        activity?.databaseEvent?.use {
            val result = select(FavoriteEvents.TABLE_EVENT_FAVORITE)
            val favorite = result.parseList(classParser<FavoriteEvents>())
            favoriteEvents.addAll(favorite)
            adapter.notifyDataSetChanged()
        }

    }
}

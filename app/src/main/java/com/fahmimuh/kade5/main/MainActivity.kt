package com.fahmimuh.kade5.main

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.fahmimuh.kade5.R
import com.fahmimuh.kade5.R.id.*
import com.fahmimuh.kade5.main.favorites.FavoritesFragment
import com.fahmimuh.kade5.main.matches.MatchesFragment
import com.fahmimuh.kade5.main.teams.TeamsFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        nav.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                matches -> loadMatchesFragment()
                teams -> loadTeamsFragment()
                favorite -> loadFavoritesFragment()
            }
            true
        }
        nav.selectedItemId = matches
    }

    private fun loadFavoritesFragment() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment, FavoritesFragment())
            .commit()
    }

    private fun loadTeamsFragment() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment, TeamsFragment())
            .commit()
    }

    private fun loadMatchesFragment() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment, MatchesFragment())
            .commit()
    }
}

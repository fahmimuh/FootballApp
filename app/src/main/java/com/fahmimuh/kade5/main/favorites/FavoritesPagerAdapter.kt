package com.fahmimuh.kade5.main.favorites

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.fahmimuh.kade5.main.favorites.match.FavoriteEventsFragment
import com.fahmimuh.kade5.main.favorites.team.FavoriteTeamsFragment

class FavoritesPagerAdapter(fm: FragmentManager): FragmentPagerAdapter(fm) {
    private val pages = listOf(
        FavoriteEventsFragment(),
        FavoriteTeamsFragment()
    )

    override fun getItem(position: Int): Fragment {
        return pages[position]
    }

    override fun getCount(): Int {
        return pages.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position){
            0 -> "Matches"
            else -> "Teams"
            }
        }
}
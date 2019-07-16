package com.fahmimuh.kade5.detail.detailteam

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.fahmimuh.kade5.detail.detailteam.overview.OverviewFragment
import com.fahmimuh.kade5.detail.detailteam.players.PlayersFragment

class DetailPagerAdapter(fm: FragmentManager): FragmentPagerAdapter(fm) {
    private val pages = listOf(
        OverviewFragment(),
        PlayersFragment()
    )

    override fun getItem(position: Int): Fragment {
        return pages[position]
    }

    override fun getCount(): Int {
        return pages.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position){
            0 -> "Overview"
            else -> "Players"
            }
        }
}
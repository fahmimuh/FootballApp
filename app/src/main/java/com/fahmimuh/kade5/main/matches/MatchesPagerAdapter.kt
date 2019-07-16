package com.fahmimuh.kade5.main.matches

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.fahmimuh.kade5.main.matches.lastevent.LastEventFragment
import com.fahmimuh.kade5.main.matches.nextevent.NextEventFragment

class MatchesPagerAdapter(fm: FragmentManager): FragmentPagerAdapter(fm) {
    private val pages = listOf(
        LastEventFragment(),
        NextEventFragment()
    )

    override fun getItem(position: Int): Fragment {
        return pages[position] as Fragment
    }

    override fun getCount(): Int {
        return pages.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position){
            0 -> "Last Event"
            else -> "Next Event"
            }
        }
}
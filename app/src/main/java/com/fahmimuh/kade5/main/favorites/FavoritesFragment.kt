package com.fahmimuh.kade5.main.favorites


import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fahmimuh.kade5.R

class FavoritesFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_favorites, container, false)
        val viewPager = view.findViewById<ViewPager>(R.id.vpFavorites)
        val tabs = view.findViewById<TabLayout>(R.id.tabs_favorites)

        viewPager.adapter = FavoritesPagerAdapter(childFragmentManager)
        tabs.setupWithViewPager(viewPager)

        return view
    }


}

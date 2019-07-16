package com.fahmimuh.kade5.detail.detailplayer

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.fahmimuh.kade5.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_player.*

class DetailPlayerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_player)
        supportActionBar?.title = "Player Detail"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val thumb= intent.getStringExtra("thumb")
        val name= intent.getStringExtra("name")
        val club= intent.getStringExtra("club")
        val pos= intent.getStringExtra("pos")
        val desc= intent.getStringExtra("desc")

        Picasso.get().load(thumb).into(imgPlayerThumb)
        tvPlayerName.text = name
        tvPlayerClub.text = club
        tvPlayerPos.text = pos
        tvPlayerDesc.text = desc
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> { super.onOptionsItemSelected(item)
            }
        }
    }
}

package com.fahmimuh.kade5.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

class TeamFavoriteDatabaseOpenHelper(context: Context) : ManagedSQLiteOpenHelper(context, "teamfavorite.db", null, 1) {
    companion object {
        private var instance: TeamFavoriteDatabaseOpenHelper? = null


        @Synchronized
        fun getInstance(context: Context): TeamFavoriteDatabaseOpenHelper {

            if (instance == null) {
                instance = TeamFavoriteDatabaseOpenHelper(context.applicationContext)
            }
            return instance!!

        }
    }


    override fun onCreate(db: SQLiteDatabase?) {
        db?.createTable(
            FavoriteTeams.TABLE_TEAM_FAVORITE, true,
            FavoriteTeams.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            FavoriteTeams.TEAM_ID to TEXT + UNIQUE,
            FavoriteTeams.TEAM_NAME to TEXT,
            FavoriteTeams.TEAM_BADGE to TEXT
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.dropTable(FavoriteTeams.TABLE_TEAM_FAVORITE, true)

    }
}

val Context.database: TeamFavoriteDatabaseOpenHelper
    get() = TeamFavoriteDatabaseOpenHelper.getInstance(applicationContext)
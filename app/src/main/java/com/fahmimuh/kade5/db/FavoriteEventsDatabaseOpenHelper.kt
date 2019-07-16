package com.fahmimuh.kade5.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

class FavoriteEventsDatabaseOpenHelper(ctx: Context):ManagedSQLiteOpenHelper(ctx,"FavoriteEvents.db",null,2) {
    companion object {
        private var instance: FavoriteEventsDatabaseOpenHelper? = null

        @Synchronized
        fun getInstance(ctx: Context): FavoriteEventsDatabaseOpenHelper {
            if (instance == null){
                instance = FavoriteEventsDatabaseOpenHelper(ctx.applicationContext)
            }
            return instance as FavoriteEventsDatabaseOpenHelper
        }
    }


    override fun onCreate(db: SQLiteDatabase?) {
        db?.createTable(FavoriteEvents.TABLE_EVENT_FAVORITE,true,
            FavoriteEvents.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            FavoriteEvents.EVENT_ID to TEXT + UNIQUE,
            FavoriteEvents.EVENT_DATE to TEXT,
            FavoriteEvents.HOME_TEAM to TEXT,
            FavoriteEvents.AWAY_TEAM to TEXT,
            FavoriteEvents.HOME_SCORE to TEXT,
            FavoriteEvents.AWAY_SCORE to TEXT)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.dropTable(FavoriteEvents.TABLE_EVENT_FAVORITE,true)
    }

}
    // Access property for Context
val Context.databaseEvent: FavoriteEventsDatabaseOpenHelper
    get() = FavoriteEventsDatabaseOpenHelper.getInstance(applicationContext)
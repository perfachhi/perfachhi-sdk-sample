package com.droidnews.data.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.droidnews.data.model.RssItem
import javax.inject.Singleton

/**
 * Created by Dogan Gulcan on 9/30/17.
 */
@Singleton
@Database(entities = arrayOf(RssItem::class), version = 1)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        const val RSS_TABLE_NAME = "RssItems"
        const val APP_DATABASE_NAME = "droid_news.db"
    }

    abstract fun rssDao(): RssDao

}
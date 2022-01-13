package com.kathir.newsreader.db

import android.content.Context
import androidx.room.Database
import com.kathir.newsreader.entity.Newsdb
import androidx.room.RoomDatabase
import com.kathir.newsreader.dao.CalDao
import kotlin.jvm.Volatile
import com.kathir.newsreader.db.NewsRoomDatabasee
import androidx.room.Room

/**
 * Created by KATHIR on 12-01-2022.
 */
@Database(entities = [Newsdb::class], version = 1)
abstract class NewsRoomDatabasee : RoomDatabase() {

    abstract fun noteDao(): CalDao?

    companion object {
        @Volatile
        var productRoomDatabase: NewsRoomDatabasee? = null
        fun getNoteRoomDatabase(context: Context): NewsRoomDatabasee? {
            if (productRoomDatabase == null) {
                synchronized(NewsRoomDatabasee::class.java) {
                    if (productRoomDatabase == null) {
                        productRoomDatabase = Room.databaseBuilder(
                            context.applicationContext,
                            NewsRoomDatabasee::class.java,
                            "note_database"
                        ).build()
                    }
                }
            }
            return productRoomDatabase
        }
    }

}
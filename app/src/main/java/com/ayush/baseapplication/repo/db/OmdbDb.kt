package com.ayush.baseapplication.repo.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ayush.baseapplication.repo.domain.OmdbItem

@Database(
    entities = [OmdbItem::class],
    version = 1,
    exportSchema = false
)
abstract class OmdbDb : RoomDatabase() {
    companion object {
        fun create(context: Context): OmdbDb {
            val databaseBuilder = Room.databaseBuilder(context, OmdbDb::class.java, "omdb.db")
            return databaseBuilder
                .fallbackToDestructiveMigration()
                .build()
        }
    }

    abstract fun posts(): OmdbDao
}
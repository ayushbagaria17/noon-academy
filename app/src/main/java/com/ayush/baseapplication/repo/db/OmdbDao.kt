package com.ayush.baseapplication.repo.db

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ayush.baseapplication.repo.domain.OmdbItem

@Dao
interface OmdbDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(posts: List<OmdbItem>)

    @Query("SELECT * FROM omdb WHERE title = :title ORDER BY indexInResponse ASC")
    fun getPosts(title: String): DataSource.Factory<Int, OmdbItem>
}
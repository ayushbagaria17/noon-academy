package com.ayush.noonacademy.repo.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ayush.noonacademy.repo.domain.OmdbItem

@Dao
interface OmdbDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(posts: List<OmdbItem>)

    @Query("SELECT * FROM omdb WHERE title LIKE '% ' || :title || ' %' OR title LIKE :title || ' %' OR title LIKE '% '||:title")
    fun getPosts(title: String): LiveData<List<OmdbItem>>
}
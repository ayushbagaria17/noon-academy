package com.ayush.noonacademy.repo.db

import androidx.lifecycle.LiveData
import com.ayush.noonacademy.repo.domain.OmdbItem
import com.ayush.noonacademy.repo.domain.OmdbSearchResponse

class OmdbDbRepoImpl(private val db: OmdbDb) : OmdbDbRepo{

    override fun insertResultIntoDb(response: OmdbSearchResponse) {
        response.list?.takeIf { it.isNotEmpty() }?.let {
            db.posts().insert(it)
        }
    }

    override fun searchQuery(query: String): LiveData<List<OmdbItem>> {
       return  db.posts().getPosts(query)
    }
}

interface OmdbDbRepo {
    fun insertResultIntoDb(response: OmdbSearchResponse)
    fun searchQuery(query: String): LiveData<List<OmdbItem>>
}
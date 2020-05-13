package com.ayush.baseapplication.repo

import com.ayush.baseapplication.repo.domain.OmdbSearchResponse
import io.reactivex.Single


class OmdbServiceImpl(private val apiKey: String,
                      private val omdbApi: OmdbApi) : OmdbService{
    override fun getResuultForQuery(query: String): Single<OmdbSearchResponse> {
        return omdbApi.getListOfSeries(apiKey = apiKey, query = query, page = 1)
    }

}

interface OmdbService {
    fun getResuultForQuery(query: String): Single<OmdbSearchResponse>
}

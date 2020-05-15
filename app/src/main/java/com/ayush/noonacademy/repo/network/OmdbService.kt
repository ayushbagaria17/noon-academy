package com.ayush.noonacademy.repo.network

import com.ayush.noonacademy.repo.domain.OmdbSearchResponse
import io.reactivex.Single


class OmdbServiceImpl(private val apiKey: String,
                      private val omdbApi: OmdbApi
) : OmdbService {
    override fun getResultForQuery(query: String): Single<OmdbSearchResponse> {
        return omdbApi.getListOfSeries(apiKey = apiKey, query = query, page = 1)
    }

}

interface OmdbService {
    fun getResultForQuery(query: String): Single<OmdbSearchResponse>
}

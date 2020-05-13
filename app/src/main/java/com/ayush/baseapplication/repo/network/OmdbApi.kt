package com.ayush.baseapplication.repo.network

import com.ayush.baseapplication.repo.domain.OmdbSearchResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface OmdbApi {
    @GET
    fun getListOfSeries(@Query("s") query: String, @Query("apiKey") apiKey: String, @Query("page") page: Int) : Single<OmdbSearchResponse>
}
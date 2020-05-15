package com.ayush.noonacademy.repo.network

import com.ayush.noonacademy.repo.domain.OmdbSearchResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface OmdbApi {
    @GET(".")
    fun getListOfSeries(@Query("s") query: String, @Query("apiKey") apiKey: String, @Query("page") page: Int) : Single<OmdbSearchResponse>
}
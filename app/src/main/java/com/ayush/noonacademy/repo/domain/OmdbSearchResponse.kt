package com.ayush.noonacademy.repo.domain

import com.google.gson.annotations.SerializedName

data class OmdbSearchResponse(
    @SerializedName("Search") val list : List<OmdbItem>?,
    @SerializedName("TotalResults") val totalResults : Int,
    @SerializedName("Response") val response : String
)
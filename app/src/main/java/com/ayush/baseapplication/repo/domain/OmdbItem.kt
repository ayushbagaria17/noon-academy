package com.ayush.baseapplication.repo.domain

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "omdb",
    indices = [Index(value = ["title"], unique = false)])
data class OmdbItem (
    @PrimaryKey
    @SerializedName("Title") val title: String,
    @SerializedName("Year") val year: String,
    @SerializedName("imdbID") val imdbID: String,
    @SerializedName("Type") val type: String,
    @SerializedName("Poster") val poster: String
) {
    var indexInResponse: Int = -1
}
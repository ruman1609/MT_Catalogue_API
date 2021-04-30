package com.rudyrachman16.mtcatalogueapi.data.api.models

import com.google.gson.annotations.SerializedName

data class TvShowList(
    val id: String,
    @SerializedName("name") val title: String,
    @SerializedName("overview") val desc: String,
    @SerializedName("first_air_date") val date: String,
    @SerializedName("poster_path") val imgUrl: String,
    @SerializedName("backdrop_path") val bgUrl: String,
    @SerializedName("vote_average") val rating: Double,
    @SerializedName("vote_count") val voter: Int
)

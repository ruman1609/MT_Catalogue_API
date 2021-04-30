package com.rudyrachman16.mtcatalogueapi.data.api.models

import com.google.gson.annotations.SerializedName

data class MovieItems(
    @SerializedName("results") val list: ArrayList<MovieList>
)
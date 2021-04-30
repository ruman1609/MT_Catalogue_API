package com.rudyrachman16.mtcatalogueapi.data.api.models

import com.google.gson.annotations.SerializedName

data class TvShowItems(
    @SerializedName("results") val list: ArrayList<TvShowList>
)

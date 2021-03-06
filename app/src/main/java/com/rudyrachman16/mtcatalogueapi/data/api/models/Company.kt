package com.rudyrachman16.mtcatalogueapi.data.api.models

import com.google.gson.annotations.SerializedName

data class Company(
    @SerializedName("logo_path") val imgURL: String,
    val name: String
)
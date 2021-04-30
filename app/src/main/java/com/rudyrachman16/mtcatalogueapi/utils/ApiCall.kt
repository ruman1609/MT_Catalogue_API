package com.rudyrachman16.mtcatalogueapi.utils

import com.rudyrachman16.mtcatalogueapi.BuildConfig
import com.rudyrachman16.mtcatalogueapi.data.api.ApiRequest
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiCall {
    private val loggingInterceptor =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    private val client = OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()

    val apiReq: ApiRequest by lazy {
        Retrofit.Builder().apply {
            addConverterFactory(GsonConverterFactory.create())
            baseUrl(BuildConfig.BASE_URL)
            client(client)
        }.build().create(ApiRequest::class.java)
    }
}
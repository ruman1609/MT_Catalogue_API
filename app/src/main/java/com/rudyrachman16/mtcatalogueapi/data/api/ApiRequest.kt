package com.rudyrachman16.mtcatalogueapi.data.api

import com.rudyrachman16.mtcatalogueapi.BuildConfig
import com.rudyrachman16.mtcatalogueapi.data.api.models.MovieDetail
import com.rudyrachman16.mtcatalogueapi.data.api.models.MovieItems
import com.rudyrachman16.mtcatalogueapi.data.api.models.TvShowDetail
import com.rudyrachman16.mtcatalogueapi.data.api.models.TvShowItems
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiRequest {
    @GET("tv/popular?api_key=${BuildConfig.API_KEY}")
    fun getTvShows(): Call<TvShowItems>

    @GET("tv/{tvShowID}?api_key=${BuildConfig.API_KEY}")
    fun getTvShow(@Path("tvShowID") id: String): Call<TvShowDetail>

    @GET("movie/popular?api_key=${BuildConfig.API_KEY}")
    fun getMovies(): Call<MovieItems>

    @GET("movie/{movieID}?api_key=${BuildConfig.API_KEY}")
    fun getMovie(@Path("movieID") id: String): Call<MovieDetail>
}
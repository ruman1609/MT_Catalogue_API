package com.rudyrachman16.mtcatalogueapi.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rudyrachman16.mtcatalogueapi.data.api.ApiCallback
import com.rudyrachman16.mtcatalogueapi.data.api.ApiDataGet
import com.rudyrachman16.mtcatalogueapi.data.api.models.MovieDetail
import com.rudyrachman16.mtcatalogueapi.data.api.models.MovieList
import com.rudyrachman16.mtcatalogueapi.data.api.models.TvShowDetail
import com.rudyrachman16.mtcatalogueapi.data.api.models.TvShowList

class FakeRepositories(private val apiDataGet: ApiDataGet) {

    fun getMovies(failed: (error: Throwable) -> Unit): LiveData<ArrayList<MovieList>> {
        val movieList = MutableLiveData<ArrayList<MovieList>>()
        apiDataGet.getMovies(object : ApiCallback<ArrayList<MovieList>> {
            override fun onSuccess(item: ArrayList<MovieList>) {
                movieList.postValue(item)
            }

            override fun onFailure(error: Throwable) {
                failed(error)
            }
        })
        return movieList
    }

    fun getTvShows(failed: (error: Throwable) -> Unit): LiveData<ArrayList<TvShowList>> {
        val tvShowList = MutableLiveData<ArrayList<TvShowList>>()
        apiDataGet.getTvShows(object : ApiCallback<ArrayList<TvShowList>> {
            override fun onSuccess(item: ArrayList<TvShowList>) {
                tvShowList.postValue(item)
            }

            override fun onFailure(error: Throwable) {
                failed(error)
            }
        })
        return tvShowList
    }

    fun getMovie(id: String, failed: (error: Throwable) -> Unit): LiveData<MovieDetail> {
        val movieDetail = MutableLiveData<MovieDetail>()
        apiDataGet.getMovie(id, object : ApiCallback<MovieDetail> {
            override fun onSuccess(item: MovieDetail) {
                movieDetail.postValue(item)
            }

            override fun onFailure(error: Throwable) {
                failed(error)
            }
        })
        return movieDetail
    }

    fun getTvShow(id: String, failed: (error: Throwable) -> Unit): LiveData<TvShowDetail> {
        val tvShowDetail = MutableLiveData<TvShowDetail>()
        apiDataGet.getTvShow(id, object : ApiCallback<TvShowDetail> {
            override fun onSuccess(item: TvShowDetail) {
                tvShowDetail.postValue(item)
            }

            override fun onFailure(error: Throwable) {
                failed(error)
            }
        })
        return tvShowDetail
    }
}
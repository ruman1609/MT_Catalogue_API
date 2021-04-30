package com.rudyrachman16.mtcatalogueapi.views.home.tabs

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.rudyrachman16.mtcatalogueapi.data.Repositories
import com.rudyrachman16.mtcatalogueapi.data.api.models.MovieList
import com.rudyrachman16.mtcatalogueapi.data.api.models.TvShowList

class TabViewModel(private val repositories: Repositories, private val application: Application) :
    ViewModel() {
    fun getMovies(): LiveData<ArrayList<MovieList>> = repositories.getMovies {
        Toast.makeText(application.applicationContext, it.message, Toast.LENGTH_LONG).show()
    }

    fun testMovies(): LiveData<ArrayList<MovieList>> = repositories.testMovies()

    fun getTvShow(): LiveData<ArrayList<TvShowList>> = repositories.getTvShows {
        Toast.makeText(application.applicationContext, it.message, Toast.LENGTH_LONG).show()
    }

    fun testTvShow(): LiveData<ArrayList<TvShowList>> = repositories.testTvShows()
}
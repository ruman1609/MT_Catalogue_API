package com.rudyrachman16.mtcatalogueapi.views.home.tabs

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.google.gson.Gson
import com.rudyrachman16.mtcatalogueapi.data.Repositories
import com.rudyrachman16.mtcatalogueapi.data.api.models.MovieItems
import com.rudyrachman16.mtcatalogueapi.data.api.models.MovieList
import com.rudyrachman16.mtcatalogueapi.data.api.models.TvShowItems
import com.rudyrachman16.mtcatalogueapi.data.api.models.TvShowList
import com.rudyrachman16.mtcatalogueapi.utils.ApiCall
import com.rudyrachman16.mtcatalogueapi.utils.DummyData
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import java.net.HttpURLConnection

@RunWith(MockitoJUnitRunner::class)
class TabViewModelTest {

    private lateinit var viewModel: TabViewModel

    private val movieList = Gson().fromJson(DummyData.jsonMovie, MovieItems::class.java).list
    private val tvShowList = Gson().fromJson(DummyData.jsonTvShow, TvShowItems::class.java).list

    @Mock
    private lateinit var repositories: Repositories

    @Mock
    private lateinit var application: Application

    @Mock
    private lateinit var movObserver: Observer<ArrayList<MovieList>>

    @Mock
    private lateinit var tvObserver: Observer<ArrayList<TvShowList>>

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var mockServer: MockWebServer

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = TabViewModel(repositories, application)

        mockServer = MockWebServer()
        mockServer.start()
    }

    @After
    fun tearDown() {
        mockServer.shutdown()
    }

    @Test
    fun testApiCall() {
        val response = MockResponse().apply {
            setResponseCode(HttpURLConnection.HTTP_OK)
            setBody(DummyData.jsonMovie)
        }
        mockServer.enqueue(response)

        val actual = ApiCall.apiReq.getMovies()
        assertEquals(
            response.toString().contains("results"),
            actual.toString().contains("results")
        )
    }

    @Test
    fun getMovies() {
        val movieList = MutableLiveData<ArrayList<MovieList>>()
        movieList.value = this.movieList

        `when`(repositories.testMovies()).thenReturn(movieList)
        val movies = viewModel.testMovies().value
        verify(repositories).testMovies()
        assertNotNull(movies)
        assertEquals(movies, this.movieList)

        viewModel.testMovies().observeForever(movObserver)
        verify(movObserver).onChanged(this.movieList)
    }

    @Test
    fun getTvShow() {
        val tvShowList = MutableLiveData<ArrayList<TvShowList>>()
        tvShowList.value = this.tvShowList

        `when`(repositories.testTvShows()).thenReturn(tvShowList)
        val tvShows = viewModel.testTvShow().value
        verify(repositories).testTvShows()
        assertNotNull(tvShows)
        assertEquals(tvShows, this.tvShowList)

        viewModel.testTvShow().observeForever(tvObserver)
        verify(tvObserver).onChanged(this.tvShowList)
    }

    @Test
    fun testEmptyArrayCase() {
        val mMovieList = viewModel.getMovies()
        val mTvShowList = viewModel.getTvShow()
        assertNull(mMovieList)
        assertNull(mTvShowList)
    }
}
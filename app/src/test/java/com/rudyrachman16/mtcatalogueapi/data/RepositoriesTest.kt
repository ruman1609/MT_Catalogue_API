package com.rudyrachman16.mtcatalogueapi.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.gson.Gson
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.verify
import com.rudyrachman16.mtcatalogueapi.data.api.ApiCallback
import com.rudyrachman16.mtcatalogueapi.data.api.ApiDataGet
import com.rudyrachman16.mtcatalogueapi.data.api.models.*
import com.rudyrachman16.mtcatalogueapi.utils.DummyData
import com.rudyrachman16.mtcatalogueapi.utils.LiveDataTestUtil
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RepositoriesTest {
    private val apiDataGet = Mockito.mock(ApiDataGet::class.java)
    private val fakeRepo = FakeRepositories(apiDataGet)

    private val movieList = Gson().fromJson(DummyData.jsonMovie, MovieItems::class.java).list
    private val tvShowList = Gson().fromJson(DummyData.jsonTvShow, TvShowItems::class.java).list

    private val dummyMovie = DummyData.dummyMovie
    private val dummyTvShow = DummyData.dummyTvShow

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun getMovies() {
        doAnswer {
            @Suppress("UNCHECKED_CAST")
            (it.arguments[0] as ApiCallback<ArrayList<MovieList>>).apply {
                onSuccess(movieList)
                onFailure(NullPointerException("Error"))
            }
            null
        }.`when`(apiDataGet).getMovies(any())
        val movieList = LiveDataTestUtil.getValue(fakeRepo.getMovies {})
        verify(apiDataGet).getMovies(any())
        assertNotNull(movieList)
        assertEquals(movieList.size, this.movieList.size)
    }

    @Test
    fun getTvShows() {
        doAnswer {
            @Suppress("UNCHECKED_CAST")
            (it.arguments[0] as ApiCallback<ArrayList<TvShowList>>).apply {
                onSuccess(tvShowList)
                onFailure(NullPointerException("Error"))
            }
            null
        }.`when`(apiDataGet).getTvShows(any())
        val tvShowList = LiveDataTestUtil.getValue(fakeRepo.getTvShows {})

        verify(apiDataGet).getTvShows(any())
        assertNotNull(tvShowList)
        assertEquals(tvShowList.size, this.tvShowList.size)
    }

    @Test
    fun getMovie() {
        doAnswer {
            @Suppress("UNCHECKED_CAST")
            (it.arguments[1] as ApiCallback<MovieDetail>).apply {
                onSuccess(dummyMovie)
                onFailure(NullPointerException("Error"))
            }
            null
        }.`when`(apiDataGet).getMovie(eq(dummyMovie.id), any())
        val movie = LiveDataTestUtil.getValue(fakeRepo.getMovie(dummyMovie.id) {})

        verify(apiDataGet).getMovie(eq(dummyMovie.id), any())
        assertNotNull(movie)
        assertEquals(movie, dummyMovie)
    }

    @Test
    fun getTvShow() {
        doAnswer {
            @Suppress("UNCHECKED_CAST")
            (it.arguments[1] as ApiCallback<TvShowDetail>).apply {
                onSuccess(dummyTvShow)
                onFailure(NullPointerException("Error"))
            }
            null
        }.`when`(apiDataGet).getTvShow(eq(dummyTvShow.id), any())
        val tvShow = LiveDataTestUtil.getValue(fakeRepo.getTvShow(dummyTvShow.id) {})

        verify(apiDataGet).getTvShow(eq(dummyTvShow.id), any())
        assertNotNull(tvShow)
        assertEquals(tvShow, dummyTvShow)
    }
}
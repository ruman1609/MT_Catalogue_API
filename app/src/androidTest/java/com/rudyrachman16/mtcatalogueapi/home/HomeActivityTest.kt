package com.rudyrachman16.mtcatalogueapi.home

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.swipeUp
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import com.google.gson.Gson
import com.rudyrachman16.mtcatalogueapi.R
import com.rudyrachman16.mtcatalogueapi.data.api.models.MovieItems
import com.rudyrachman16.mtcatalogueapi.data.api.models.TvShowItems
import com.rudyrachman16.mtcatalogueapi.utils.DummyData
import com.rudyrachman16.mtcatalogueapi.utils.IdlingResource
import com.rudyrachman16.mtcatalogueapi.views.home.HomeActivity
import org.junit.After
import org.junit.Before
import org.junit.Test

class HomeActivityTest {
    private val movieList = Gson().fromJson(DummyData.jsonMovie, MovieItems::class.java)
    private val tvShowList = Gson().fromJson(DummyData.jsonTvShow, TvShowItems::class.java)

    @Before
    fun setUp() {
        ActivityScenario.launch(HomeActivity::class.java)
        IdlingRegistry.getInstance().register(IdlingResource.idlingResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(IdlingResource.idlingResource)
    }

    @Test
    fun testLoadMovies() {
        onView(withId(R.id.tabRecycler)).check(matches(isDisplayed()))
        onView(withId(R.id.tabRecycler)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                movieList.list.size - 1
            )
        )
    }

    @Test
    fun testLoadTvShow() {
        onView(withText(R.string.tv_shows)).perform(click())
        onView(withId(R.id.tabRecycler)).check(matches(isDisplayed()))
        onView(withId(R.id.tabRecycler)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                tvShowList.list.size - 1
            )
        )
    }

    @Test
    fun testLoadDetail() {
        onView(withId(R.id.tabRecycler)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click())
        )
        onView(withId(R.id.detType)).check(matches(isDisplayed()))
        onView(withId(R.id.detAppBarLayout)).perform(swipeUp())
        onView(withId(R.id.detDesc)).check(matches(isDisplayed()))
    }
}
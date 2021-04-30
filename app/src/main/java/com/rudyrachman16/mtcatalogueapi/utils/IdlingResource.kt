package com.rudyrachman16.mtcatalogueapi.utils

import androidx.test.espresso.idling.CountingIdlingResource

object IdlingResource {
    private const val RESOURCE = "GLOBAL"

    val idlingResource = CountingIdlingResource(RESOURCE)

    fun increment() {
        idlingResource.increment()
    }

    fun decrement() {
        idlingResource.decrement()
    }
}
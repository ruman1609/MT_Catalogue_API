package com.rudyrachman16.mtcatalogueapi.data.api

interface ApiCallback<T> {
    fun onSuccess(item: T)
    fun onFailure(error: Throwable)
}
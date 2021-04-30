package com.rudyrachman16.mtcatalogueapi.di

import com.rudyrachman16.mtcatalogueapi.data.Repositories
import com.rudyrachman16.mtcatalogueapi.data.api.ApiDataGet

object Injection {
    fun provideRepositories(): Repositories = Repositories.getInstance(ApiDataGet.getInstance())
}
package com.sample.topmovies.di

import com.sample.topmovies.repository.network.MovieService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun providesUrl() = "https://api.themoviedb.org/3/"

    @Provides
    @Singleton
    fun providesApiService(url:String) : MovieService =
        Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieService::class.java)
}
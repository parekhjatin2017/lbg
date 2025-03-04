package com.lbg.data.di

import com.lbg.data.Constants
import com.lbg.data.repository.MoviesRepositoryImpl
import com.lbg.data.repository.network.MovieService
import com.lbg.data.repository.network.MovieServiceApi
import com.lbg.domain.repository.IMoviesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    fun providesUrl() = Constants.BASE_URL

    @Provides
    @Singleton
    fun providesApiService(url: String): MovieService =
        Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieService::class.java)

    @Provides
    @Singleton
    fun providesMoviesRepository(movieServiceApi: MovieServiceApi): IMoviesRepository =
        MoviesRepositoryImpl(movieServiceApi)
}
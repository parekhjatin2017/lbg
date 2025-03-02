package com.lbg.domain.usecases.di

import com.lbg.data.repository.IMoviesRepository
import com.lbg.domain.usecases.GetMovieDetailUseCase
import com.lbg.domain.usecases.GetPopularMoviesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Provides
    fun provideGetMovieDetailUseCase(moviesRepository: IMoviesRepository): GetMovieDetailUseCase {
        return GetMovieDetailUseCase(moviesRepository)
    }

    @Provides
    fun provideGetPopularMoviesUseCase(moviesRepository: IMoviesRepository): GetPopularMoviesUseCase {
        return GetPopularMoviesUseCase(moviesRepository)
    }
}
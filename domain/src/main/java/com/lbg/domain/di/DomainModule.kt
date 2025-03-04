package com.lbg.domain.di

import com.lbg.domain.repository.IMoviesRepository
import com.lbg.domain.usecases.GetMovieDetailUseCase
import com.lbg.domain.usecases.GetPopularMoviesUseCase
import dagger.Module
import dagger.Provides

@Module
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
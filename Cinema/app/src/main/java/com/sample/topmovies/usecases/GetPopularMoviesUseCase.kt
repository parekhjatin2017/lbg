package com.sample.topmovies.usecases

import com.sample.topmovies.Constants
import com.sample.topmovies.model.MovieList
import com.sample.topmovies.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPopularMoviesUseCase @Inject
constructor(private val mainRepository: MoviesRepository) {

    fun getPopularMovies(): Flow<MovieList> {
        return mainRepository.getPopularMovies(Constants.API_KEY)
    }
}
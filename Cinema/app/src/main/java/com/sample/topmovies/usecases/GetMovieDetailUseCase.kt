package com.sample.topmovies.usecases

import com.sample.topmovies.Constants
import com.sample.topmovies.model.MovieDetail
import com.sample.topmovies.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMovieDetailUseCase @Inject
constructor(private val mainRepository: MoviesRepository) {

    fun getMovieDetail(movieId: String): Flow<MovieDetail> {
        return mainRepository.getMovieDetail(Constants.API_KEY, movieId)
    }
}
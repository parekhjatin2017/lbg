package com.lbg.domain.usecases

import com.lbg.data.repository.MoviesRepository
import com.lbg.model.Constants
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPopularMoviesUseCase @Inject
constructor(private val mainRepository: MoviesRepository) {

    fun getPopularMovies(): Flow<com.lbg.model.MovieList> {
        return mainRepository.getPopularMovies(Constants.API_KEY)
    }
}
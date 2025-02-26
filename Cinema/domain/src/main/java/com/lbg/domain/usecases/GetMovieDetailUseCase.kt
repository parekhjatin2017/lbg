package com.lbg.domain.usecases

import com.lbg.data.repository.MoviesRepository
import com.lbg.model.Constants
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMovieDetailUseCase @Inject
constructor(private val mainRepository: MoviesRepository) {

    fun getMovieDetail(movieId: String): Flow<com.lbg.model.MovieDetail> {
        return mainRepository.getMovieDetail(Constants.API_KEY, movieId)
    }
}
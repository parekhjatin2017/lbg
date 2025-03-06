package com.lbg.domain.usecases

import com.lbg.domain.Constants
import com.lbg.domain.model.ApiStatus
import com.lbg.domain.model.FilmDetails
import com.lbg.domain.repository.IMoviesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetMovieDetailUseCase @Inject
constructor(private val moviesRepository: IMoviesRepository) {
    operator fun invoke(movieId: String): Flow<ApiStatus<FilmDetails>> = flow {
        emit(ApiStatus.Loading)
        emit(moviesRepository.getMovieDetail(Constants.API_KEY, movieId))
    }.flowOn(Dispatchers.IO)
}
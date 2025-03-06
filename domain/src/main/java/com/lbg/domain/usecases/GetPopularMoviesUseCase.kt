package com.lbg.domain.usecases

import com.lbg.domain.Constants
import com.lbg.domain.model.ApiStatus
import com.lbg.domain.model.Film
import com.lbg.domain.repository.IMoviesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetPopularMoviesUseCase @Inject
constructor(private val moviesRepository: IMoviesRepository) {
    operator fun invoke(): Flow<ApiStatus<List<Film>>> = flow {
        emit(ApiStatus.Loading)
        emit(moviesRepository.getPopularMovies(Constants.API_KEY))
    }.flowOn(Dispatchers.IO)
}

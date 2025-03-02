package com.lbg.domain.usecases

import com.lbg.data.model.Resource
import com.lbg.data.repository.IMoviesRepository
import com.lbg.domain.model.ApiStatus
import com.lbg.domain.model.FilmDetails
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetMovieDetailUseCase @Inject
constructor(private val moviesRepository: IMoviesRepository) {
    fun getMovieDetail(movieId: String): Flow<ApiStatus> = flow {
        emit(ApiStatus.Loading)
        when (val response = moviesRepository.getMovieDetail(Constants.API_KEY, movieId)) {
            is Resource.Error -> {
                emit(ApiStatus.Error(response.message))
            }

            is Resource.Success -> {
                response.data?.let {
                    emit(
                        ApiStatus.MovieDetailSuccess(
                            FilmDetails(it.title, it.overview, it.poster_path)
                        )
                    )
                } ?: run {
                    emit(ApiStatus.Error("Server error"))
                }
            }
        }
    }.flowOn(Dispatchers.IO)
}
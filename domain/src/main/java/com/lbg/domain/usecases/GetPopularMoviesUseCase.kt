package com.lbg.domain.usecases

import com.lbg.data.model.Resource
import com.lbg.data.repository.IMoviesRepository
import com.lbg.domain.model.ApiStatus
import com.lbg.domain.model.Film
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetPopularMoviesUseCase @Inject
constructor(private val moviesRepository: IMoviesRepository) {

    fun getPopularMovies(): Flow<ApiStatus> = flow {
        emit(ApiStatus.Loading)
        when (val response = moviesRepository.getPopularMovies(Constants.API_KEY)) {
            is Resource.Error -> {
                emit(ApiStatus.Error(response.message))
            }

            is Resource.Success -> {
                response.data?.let {
                    val films = ArrayList<Film>()
                    it.results.forEach { movie ->
                        films.add(
                            Film(
                                movie.id.toString(),
                                movie.title,
                                movie.adult,
                                movie.release_date,
                                movie.poster_path
                            )
                        )
                    }
                    emit(ApiStatus.MovieListSuccess(films))

                } ?: run {
                    emit(ApiStatus.Error("Server error"))
                }

            }
        }
    }.flowOn(Dispatchers.IO)
}
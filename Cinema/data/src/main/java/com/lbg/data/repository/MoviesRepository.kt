package com.lbg.data.repository

import com.lbg.data.repository.network.MovieServiceApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MoviesRepository @Inject constructor(private val movieServiceImpl: MovieServiceApi) {

    fun getPopularMovies(apiKey: String):Flow<com.lbg.model.MovieList> = flow {
        emit(movieServiceImpl.getPopularMovies(apiKey))
    }.flowOn(Dispatchers.IO)

    fun getMovieDetail(apiKey: String, movieId: String):Flow<com.lbg.model.MovieDetail> = flow {
        emit(movieServiceImpl.getMovieDetail(apiKey, movieId))
    }.flowOn(Dispatchers.IO)
}
package com.sample.topmovies.repository

import com.sample.topmovies.model.MovieDetail
import com.sample.topmovies.model.MovieList
import com.sample.topmovies.repository.network.MovieServiceApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MoviesRepository @Inject constructor(private val movieServiceImpl: MovieServiceApi) {

    fun getPopularMovies(apiKey: String):Flow<MovieList> = flow {
        emit(movieServiceImpl.getPopularMovies(apiKey))
    }.flowOn(Dispatchers.IO)

    fun getMovieDetail(apiKey: String, movieId: String):Flow<MovieDetail> = flow {
        emit(movieServiceImpl.getMovieDetail(apiKey, movieId))
    }.flowOn(Dispatchers.IO)
}
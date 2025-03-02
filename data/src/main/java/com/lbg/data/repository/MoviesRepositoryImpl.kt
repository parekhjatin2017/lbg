package com.lbg.data.repository

import com.lbg.data.model.MovieDetail
import com.lbg.data.model.MovieList
import com.lbg.data.model.Resource
import com.lbg.data.repository.network.MovieServiceApi

class MoviesRepositoryImpl(private val movieServiceApi: MovieServiceApi) :
    IMoviesRepository {

    override suspend fun getPopularMovies(apiKey: String): Resource<MovieList> {
        return try {
            Resource.Success(movieServiceApi.getPopularMovies(apiKey))
        } catch (e: Exception) {
            Resource.Error(e.message.toString())
        }
    }

    override suspend fun getMovieDetail(apiKey: String, movieId: String): Resource<MovieDetail> {
        return try {
            Resource.Success(movieServiceApi.getMovieDetail(apiKey, movieId))
        } catch (e: Exception) {
            Resource.Error(e.message.toString())
        }
    }
}
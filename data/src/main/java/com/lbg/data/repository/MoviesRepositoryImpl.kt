package com.lbg.data.repository

import com.lbg.data.model.toFilmDetail
import com.lbg.data.model.toFilmList
import com.lbg.data.repository.network.MovieServiceApi
import com.lbg.domain.model.ApiStatus
import com.lbg.domain.model.Film
import com.lbg.domain.model.FilmDetails
import com.lbg.domain.repository.IMoviesRepository
import java.io.IOException

class MoviesRepositoryImpl(private val movieServiceApi: MovieServiceApi) :
    IMoviesRepository {

    override suspend fun getPopularMovies(apiKey: String): ApiStatus<List<Film>> {
        return try {
            ApiStatus.Success(movieServiceApi.getPopularMovies(apiKey).toFilmList())
        } catch (e: Exception) {
            when (e) {
                is IOException -> ApiStatus.NetworkError
                else -> {
                    ApiStatus.Error(e.message)
                }
            }
        }
    }

    override suspend fun getMovieDetail(apiKey: String, movieId: String): ApiStatus<FilmDetails> {
        return try {
            ApiStatus.Success(movieServiceApi.getMovieDetail(apiKey, movieId).toFilmDetail())
        } catch (e: Exception) {
            when (e) {
                is IOException -> ApiStatus.NetworkError
                else -> {
                    ApiStatus.Error(e.message)
                }
            }
        }
    }
}
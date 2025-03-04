package com.lbg.data.repository

import com.lbg.data.model.toFilmDetail
import com.lbg.data.model.toFilmList
import com.lbg.data.repository.network.MovieServiceApi
import com.lbg.domain.model.Film
import com.lbg.domain.model.FilmDetails
import com.lbg.domain.model.Resource
import com.lbg.domain.repository.IMoviesRepository

class MoviesRepositoryImpl(private val movieServiceApi: MovieServiceApi) :
    IMoviesRepository {

    override suspend fun getPopularMovies(apiKey: String): Resource<List<Film>> {
        return try {
            Resource.Success(movieServiceApi.getPopularMovies(apiKey).toFilmList())
        } catch (e: Exception) {
            Resource.Error(e.message.toString())
        }
    }

    override suspend fun getMovieDetail(apiKey: String, movieId: String): Resource<FilmDetails> {
        return try {
            Resource.Success(movieServiceApi.getMovieDetail(apiKey, movieId).toFilmDetail())
        } catch (e: Exception) {
            Resource.Error(e.message.toString())
        }
    }
}
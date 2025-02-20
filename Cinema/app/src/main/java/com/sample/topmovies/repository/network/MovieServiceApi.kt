package com.sample.topmovies.repository.network

import com.sample.topmovies.model.MovieList
import com.sample.topmovies.model.MovieDetail
import javax.inject.Inject

class MovieServiceApi @Inject constructor(private val movieService: MovieService) {

    suspend fun getPopularMovies(apiKey: String) : MovieList {
        return movieService.getPopularMovies(apiKey)
    }

    suspend fun getMovieDetail(apiKey: String, movieId: String) : MovieDetail {
        return movieService.getMovieDetail(movieId, apiKey)
    }
}
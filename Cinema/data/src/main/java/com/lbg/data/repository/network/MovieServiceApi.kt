package com.lbg.data.repository.network

import javax.inject.Inject

class MovieServiceApi @Inject constructor(private val movieService: MovieService) {

    suspend fun getPopularMovies(apiKey: String) : com.lbg.model.MovieList {
        return movieService.getPopularMovies(apiKey)
    }

    suspend fun getMovieDetail(apiKey: String, movieId: String) : com.lbg.model.MovieDetail {
        return movieService.getMovieDetail(movieId, apiKey)
    }
}
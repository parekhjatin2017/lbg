package com.lbg.data.repository.network

import com.lbg.data.model.MovieDetail
import com.lbg.data.model.MovieList
import javax.inject.Inject

class MovieServiceApi @Inject constructor(private val movieService: MovieService) {

    suspend fun getPopularMovies(apiKey: String) : MovieList {
        return movieService.getPopularMovies(apiKey)
    }

    suspend fun getMovieDetail(apiKey: String, movieId: String) : MovieDetail {
        return movieService.getMovieDetail(movieId, apiKey)
    }
}
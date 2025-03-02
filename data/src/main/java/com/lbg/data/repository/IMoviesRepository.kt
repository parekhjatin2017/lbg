package com.lbg.data.repository

import com.lbg.data.model.MovieDetail
import com.lbg.data.model.MovieList
import com.lbg.data.model.Resource

interface IMoviesRepository {

    suspend fun getPopularMovies(apiKey: String): Resource<MovieList>

    suspend fun getMovieDetail(apiKey: String, movieId: String): Resource<MovieDetail>
}
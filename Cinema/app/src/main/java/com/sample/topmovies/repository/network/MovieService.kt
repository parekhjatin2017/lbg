package com.sample.topmovies.repository.network

import com.sample.topmovies.model.MovieList
import com.sample.topmovies.model.MovieDetail
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {

    @GET("movie/popular")
    suspend fun getPopularMovies(@Query("api_key") apiKey: String): MovieList

    @GET("movie/{movieId}")
    suspend fun getMovieDetail(@Path("movieId") movieId: String,
                       @Query("api_key") apiKey: String): MovieDetail

}
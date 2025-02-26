package com.lbg.data.repository.network

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {

    @GET("movie/popular")
    suspend fun getPopularMovies(@Query("api_key") apiKey: String): com.lbg.model.MovieList

    @GET("movie/{movieId}")
    suspend fun getMovieDetail(@Path("movieId") movieId: String,
                       @Query("api_key") apiKey: String): com.lbg.model.MovieDetail

}
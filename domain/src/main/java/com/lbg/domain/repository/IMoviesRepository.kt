package com.lbg.domain.repository

import com.lbg.domain.model.ApiStatus
import com.lbg.domain.model.Film
import com.lbg.domain.model.FilmDetails

interface IMoviesRepository {

    suspend fun getPopularMovies(apiKey: String): ApiStatus<List<Film>>

    suspend fun getMovieDetail(apiKey: String, movieId: String): ApiStatus<FilmDetails>
}
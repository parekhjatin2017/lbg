package com.lbg.domain.repository

import com.lbg.domain.model.Film
import com.lbg.domain.model.FilmDetails
import com.lbg.domain.model.Resource

interface IMoviesRepository {

    suspend fun getPopularMovies(apiKey: String): Resource<List<Film>>

    suspend fun getMovieDetail(apiKey: String, movieId: String): Resource<FilmDetails>
}
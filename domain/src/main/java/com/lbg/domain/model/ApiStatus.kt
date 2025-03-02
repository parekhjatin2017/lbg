package com.lbg.domain.model

sealed class ApiStatus {
    data object Idle : ApiStatus()
    data object Loading : ApiStatus()
    class MovieListSuccess(val data: List<Film>) : ApiStatus()
    class MovieDetailSuccess(val data: FilmDetails) : ApiStatus()
    class Error(val message: String) : ApiStatus()
}
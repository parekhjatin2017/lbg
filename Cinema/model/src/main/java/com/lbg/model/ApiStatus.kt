package com.lbg.model


sealed class ApiStatus {
    data object Idle : ApiStatus()
    data object Loading : ApiStatus()
    class MovieListSuccess(val data: MovieList) : ApiStatus()
    class MovieDetailSuccess(val data: MovieDetail) : ApiStatus()
    class Failure(val msg: Throwable) : ApiStatus()
}

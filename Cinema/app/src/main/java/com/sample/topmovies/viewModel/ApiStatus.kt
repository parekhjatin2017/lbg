package com.sample.topmovies.viewModel

import com.sample.topmovies.model.MovieDetail
import com.sample.topmovies.model.MovieList


sealed class ApiStatus{
    data object Idle : ApiStatus()
    data object Loading : ApiStatus()
    class MovieListSuccess(val data : MovieList) : ApiStatus()
    class MovieDetailSuccess(val data : MovieDetail) : ApiStatus()
    class Failure(val msg:Throwable) : ApiStatus()
}

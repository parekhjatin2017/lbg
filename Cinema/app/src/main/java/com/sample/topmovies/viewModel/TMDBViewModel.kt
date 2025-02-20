package com.sample.topmovies.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.topmovies.usecases.GetMovieDetailUseCase
import com.sample.topmovies.usecases.GetPopularMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TMDBViewModel
@Inject
constructor(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val getMovieDetailUseCase: GetMovieDetailUseCase
) : ViewModel() {

    private val _movieStateFlow: MutableStateFlow<ApiStatus> = MutableStateFlow(ApiStatus.Idle)
    val movieStateFlow: StateFlow<ApiStatus> = _movieStateFlow

    fun getPopularMovies() = viewModelScope.launch {
        _movieStateFlow.value = ApiStatus.Loading
        getPopularMoviesUseCase.getPopularMovies()
            .catch { e ->
                _movieStateFlow.value = ApiStatus.Failure(e)
            }.collect { data ->
                _movieStateFlow.value = ApiStatus.MovieListSuccess(data)
            }
    }

    fun getMovieDetail(movieId: String) = viewModelScope.launch {
        _movieStateFlow.value = ApiStatus.Loading
        getMovieDetailUseCase.getMovieDetail(movieId)
            .catch { e ->
                _movieStateFlow.value = ApiStatus.Failure(e)
            }.collect { data ->
                _movieStateFlow.value = ApiStatus.MovieDetailSuccess(data)
            }
    }
}
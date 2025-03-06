package com.lbg.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lbg.domain.model.ApiStatus
import com.lbg.domain.model.Film
import com.lbg.domain.usecases.GetPopularMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel
@Inject
constructor(private val getPopularMoviesUseCase: GetPopularMoviesUseCase) : ViewModel() {

    private val _movieListStateFlow: MutableStateFlow<ApiStatus<List<Film>>> =
        MutableStateFlow(ApiStatus.Idle)
    val movieListStateFlow: StateFlow<ApiStatus<List<Film>>> = _movieListStateFlow

    init {
        getPopularMovies()
    }

    private fun getPopularMovies() = viewModelScope.launch {
        _movieListStateFlow.value = ApiStatus.Loading
        getPopularMoviesUseCase().collect { data ->
            _movieListStateFlow.value = data
        }
    }
}
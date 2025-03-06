package com.lbg.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lbg.domain.model.ApiStatus
import com.lbg.domain.model.FilmDetails
import com.lbg.domain.usecases.GetMovieDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel
@Inject
constructor(private val getMovieDetailUseCase: GetMovieDetailUseCase) : ViewModel() {

    private val _movieDetailStateFlow: MutableStateFlow<ApiStatus<FilmDetails>> =
        MutableStateFlow(ApiStatus.Idle)
    val movieDetailStateFlow: StateFlow<ApiStatus<FilmDetails>> = _movieDetailStateFlow

    fun getMovieDetail(movieId: String) = viewModelScope.launch {
        _movieDetailStateFlow.value = ApiStatus.Loading
        getMovieDetailUseCase(movieId).collect { data ->
            _movieDetailStateFlow.value = data
        }
    }
}
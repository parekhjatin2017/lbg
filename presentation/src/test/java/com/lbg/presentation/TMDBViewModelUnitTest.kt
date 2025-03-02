package com.lbg.presentation

import app.cash.turbine.test
import com.lbg.domain.model.ApiStatus
import com.lbg.domain.model.Film
import com.lbg.domain.model.FilmDetails
import com.lbg.domain.usecases.GetMovieDetailUseCase
import com.lbg.domain.usecases.GetPopularMoviesUseCase
import com.lbg.presentation.viewmodel.TMDBViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
@ExperimentalCoroutinesApi
class TMDBViewModelUnitTest {

    private val getPopularMoviesUseCase = Mockito.mock(GetPopularMoviesUseCase::class.java)
    private val getMovieDetailUseCase = Mockito.mock(GetMovieDetailUseCase::class.java)
    private val viewModel: TMDBViewModel =
        TMDBViewModel(getPopularMoviesUseCase, getMovieDetailUseCase)
    private val id = "5"

    @Test
    fun testMovieDetails() {

        runTest {
            val detail = ApiStatus.MovieDetailSuccess(Mockito.mock(FilmDetails::class.java))
            Mockito.doReturn(flow { emit(detail) }).`when`(getMovieDetailUseCase)
                .getMovieDetail(anyString())

            viewModel.movieStateFlow.test {
                viewModel.getMovieDetail(id)
                assertEquals(ApiStatus.Idle, awaitItem())
                assertEquals(ApiStatus.Loading, awaitItem())
                assertEquals(
                    detail.javaClass, awaitItem().javaClass
                )
                cancelAndIgnoreRemainingEvents()

                Mockito.verify(getMovieDetailUseCase, Mockito.times(1)).getMovieDetail(anyString())
            }
        }
    }

    @Test
    fun testMovieList() {

        runTest {
            val list = ApiStatus.MovieListSuccess(listOf(Mockito.mock(Film::class.java)))
            Mockito.doReturn(flow { emit(list) }).`when`(getPopularMoviesUseCase).getPopularMovies()

            viewModel.movieStateFlow.test {
                viewModel.getPopularMovies()
                assertEquals(ApiStatus.Idle, awaitItem())
                assertEquals(ApiStatus.Loading, awaitItem())
                assertEquals(
                    list.javaClass, awaitItem().javaClass
                )
                cancelAndIgnoreRemainingEvents()

                Mockito.verify(
                    getPopularMoviesUseCase,
                    Mockito.times(1)
                ).getPopularMovies()
            }
        }
    }
}
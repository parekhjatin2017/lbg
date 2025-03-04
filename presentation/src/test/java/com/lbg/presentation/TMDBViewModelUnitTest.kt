package com.lbg.presentation

import app.cash.turbine.test
import com.lbg.domain.model.ApiStatus
import com.lbg.domain.model.Film
import com.lbg.domain.model.FilmDetails
import com.lbg.domain.model.Resource
import com.lbg.domain.repository.IMoviesRepository
import com.lbg.domain.usecases.GetMovieDetailUseCase
import com.lbg.domain.usecases.GetPopularMoviesUseCase
import com.lbg.presentation.viewmodel.TMDBViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
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

    private val repository = Mockito.mock(IMoviesRepository::class.java)
    private val getPopularMoviesUseCase = GetPopularMoviesUseCase(repository)
    private val getMovieDetailUseCase = GetMovieDetailUseCase(repository)
    private val viewModel: TMDBViewModel =
        TMDBViewModel(getPopularMoviesUseCase, getMovieDetailUseCase)
    private val id = "5"

    @Test
    fun testMovieDetails() {

        runTest {
            val detail = Mockito.mock(FilmDetails::class.java)
            Mockito.doReturn(Resource.Success(detail)).`when`(repository)
                .getMovieDetail(anyString(), anyString())

            viewModel.movieStateFlow.test {
                viewModel.getMovieDetail(id)
                assertEquals(ApiStatus.Idle, awaitItem())
                assertEquals(ApiStatus.Loading, awaitItem())
                assertEquals(
                    ApiStatus.MovieDetailSuccess(detail).javaClass, awaitItem().javaClass
                )
                cancelAndIgnoreRemainingEvents()
            }
        }
    }

    @Test
    fun testMovieList() {

        runTest {
            val list = listOf(Mockito.mock(Film::class.java))
            Mockito.doReturn(Resource.Success(list)).`when`(repository)
                .getPopularMovies(anyString())

            viewModel.movieStateFlow.test {
                viewModel.getPopularMovies()
                assertEquals(ApiStatus.Idle, awaitItem())
                assertEquals(ApiStatus.Loading, awaitItem())
                assertEquals(
                    ApiStatus.MovieListSuccess(list).javaClass, awaitItem().javaClass
                )
                cancelAndIgnoreRemainingEvents()
            }
        }
    }
}
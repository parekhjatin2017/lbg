package com.lbg.presentation

import app.cash.turbine.test
import com.lbg.domain.model.ApiStatus
import com.lbg.domain.model.Film
import com.lbg.domain.repository.IMoviesRepository
import com.lbg.domain.usecases.GetPopularMoviesUseCase
import com.lbg.presentation.viewmodel.MovieListViewModel
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
class MovieListViewModelUnitTest {

    private val repository = Mockito.mock(IMoviesRepository::class.java)
    private val getPopularMoviesUseCase = GetPopularMoviesUseCase(repository)

    @Test
    fun testGetMovieListApiStatusSuccess() {

        runTest {
            val list = listOf(Mockito.mock(Film::class.java))
            Mockito.doReturn(ApiStatus.Success(list)).`when`(repository)
                .getPopularMovies(anyString())

            MovieListViewModel(getPopularMoviesUseCase).movieListStateFlow.test {
                assertEquals(ApiStatus.Loading, awaitItem())
                assertEquals(
                    ApiStatus.Success(list).javaClass, awaitItem().javaClass
                )
                cancelAndIgnoreRemainingEvents()
            }
        }
    }
}
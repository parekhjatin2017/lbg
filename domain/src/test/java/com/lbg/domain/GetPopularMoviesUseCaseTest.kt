package com.lbg.domain

import app.cash.turbine.test
import com.lbg.domain.model.ApiStatus
import com.lbg.domain.model.Film
import com.lbg.domain.repository.IMoviesRepository
import com.lbg.domain.usecases.GetPopularMoviesUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito

@ExperimentalCoroutinesApi
class GetPopularMoviesUseCaseTest {

    private val repository = Mockito.mock(IMoviesRepository::class.java)
    private val id = "5"

    @Test
    fun testGetMovieListSuccessReceived() {
        runTest {
            val list = listOf(Mockito.mock(Film::class.java))
            Mockito.doReturn(ApiStatus.Success(list)).`when`(repository)
                .getPopularMovies(anyString())

            GetPopularMoviesUseCase(repository)().test {
                assert(ApiStatus.Loading == awaitItem())
                assert(ApiStatus.Success(list).javaClass == awaitItem().javaClass)
                cancelAndIgnoreRemainingEvents()
            }
        }
    }

    @Test
    fun negativeTestGetMovieListErrorReceived() {
        runTest {
            Mockito.doReturn(ApiStatus.Error(id)).`when`(repository)
                .getPopularMovies(anyString())

            GetPopularMoviesUseCase(repository)().test {
                assert(ApiStatus.Loading == awaitItem())
                assert(ApiStatus.Error(id) == awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
        }
    }

    @Test
    fun negativeTestGetMovieListNetworkErrorReceived() {
        runTest {
            Mockito.doReturn(ApiStatus.NetworkError).`when`(repository)
                .getPopularMovies(anyString())

            GetPopularMoviesUseCase(repository)().test {
                assert(ApiStatus.Loading == awaitItem())
                assert(ApiStatus.NetworkError == awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
        }
    }
}
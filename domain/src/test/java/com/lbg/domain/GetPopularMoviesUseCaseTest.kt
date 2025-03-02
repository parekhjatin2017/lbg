package com.lbg.domain

import app.cash.turbine.test
import com.lbg.data.model.MovieList
import com.lbg.data.repository.MoviesRepositoryImpl
import com.lbg.data.repository.network.MovieService
import com.lbg.data.repository.network.MovieServiceApi
import com.lbg.domain.model.ApiStatus
import com.lbg.domain.model.Film
import com.lbg.domain.usecases.GetPopularMoviesUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
@ExperimentalCoroutinesApi
class GetPopularMoviesUseCaseTest {


    private val movieService = Mockito.mock(MovieService::class.java)
    private val movieServiceImpl = MovieServiceApi(movieService)
    private val repository = MoviesRepositoryImpl(movieServiceImpl)

    @Test
    fun testMovieList() {
        runTest {
            val detail = Mockito.mock(MovieList::class.java, Mockito.RETURNS_DEEP_STUBS)
            Mockito.doReturn(detail).`when`(movieService)
                .getPopularMovies(ArgumentMatchers.anyString())

            GetPopularMoviesUseCase(repository).getPopularMovies().test {
                assertEquals(ApiStatus.Loading, awaitItem())
                assert(
                    ApiStatus.MovieListSuccess(listOf(Mockito.mock(Film::class.java))).javaClass
                            == awaitItem().javaClass
                )
                cancelAndIgnoreRemainingEvents()
            }
        }
    }

    @Test
    fun negativeTestMovieList() {
        runTest {
            Mockito.doReturn(null).`when`(movieService)
                .getMovieDetail(ArgumentMatchers.anyString(), ArgumentMatchers.anyString())

            GetPopularMoviesUseCase(repository).getPopularMovies().test {
                assertEquals(ApiStatus.Loading, awaitItem())
                assert(ApiStatus.Error("").javaClass == awaitItem().javaClass)
                cancelAndIgnoreRemainingEvents()
            }
        }
    }
}
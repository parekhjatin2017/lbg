package com.sample.topmovies

import app.cash.turbine.test
import com.sample.topmovies.repository.MoviesRepository
import com.sample.topmovies.repository.network.MovieService
import com.sample.topmovies.repository.network.MovieServiceApi
import com.lbg.domain.usecases.GetMovieDetailUseCase
import com.lbg.domain.usecases.GetPopularMoviesUseCase
import com.lbg.viewmodel.TMDBViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
@ExperimentalCoroutinesApi
class TMDBViewModelUnitTest {


    private var movieService = Mockito.mock(MovieService::class.java)

    private val movieServiceImpl = MovieServiceApi(movieService)
    private val repository = MoviesRepository(movieServiceImpl)
    private val getPopularMoviesUseCase = GetPopularMoviesUseCase(repository)
    private val getMovieDetailUseCase = GetMovieDetailUseCase(repository)
    private val viewModel: TMDBViewModel =
        TMDBViewModel(getPopularMoviesUseCase, getMovieDetailUseCase)
    private val dispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun testMovieDetails() {

        runTest {
            val detail = Mockito.mock(com.lbg.model.MovieDetail::class.java)
            Mockito.doReturn(detail)
                .`when`(movieService)
                .getMovieDetail(ArgumentMatchers.anyString(), ArgumentMatchers.anyString())


            viewModel.movieStateFlow.test {
                viewModel.getMovieDetail("9")
                assertEquals(com.lbg.model.ApiStatus.Idle, awaitItem())
                assertEquals(com.lbg.model.ApiStatus.Loading, awaitItem())
                assertEquals(com.lbg.model.ApiStatus.MovieDetailSuccess(detail).javaClass, awaitItem().javaClass)
                cancelAndIgnoreRemainingEvents()

                Mockito.verify(movieService, Mockito.times(1))
                    .getMovieDetail(ArgumentMatchers.anyString(), ArgumentMatchers.anyString())
            }
        }
    }

    @Test
    fun testMovieList() {

        runTest {
            val list = Mockito.mock(com.lbg.model.MovieList::class.java)
            Mockito.doReturn(list)
                .`when`(movieService)
                .getPopularMovies(ArgumentMatchers.anyString())


            viewModel.movieStateFlow.test {
                viewModel.getPopularMovies()
                assertEquals(com.lbg.model.ApiStatus.Idle, awaitItem())
                assertEquals(com.lbg.model.ApiStatus.Loading, awaitItem())
                assertEquals(com.lbg.model.ApiStatus.MovieListSuccess(list).javaClass, awaitItem().javaClass)
                cancelAndIgnoreRemainingEvents()

                Mockito.verify(movieService, Mockito.times(1))
                    .getPopularMovies(ArgumentMatchers.anyString())
            }
        }
    }

    @Test
    fun testMovieDetailsError() {

        runTest {

            `when`(movieService.getMovieDetail(anyString(), anyString())).thenThrow(
                NullPointerException::class.java
            )

            viewModel.movieStateFlow.test {
                viewModel.getMovieDetail("9")
                assertEquals(com.lbg.model.ApiStatus.Idle, awaitItem())
                assertEquals(com.lbg.model.ApiStatus.Loading, awaitItem())
                assertEquals(
                    com.lbg.model.ApiStatus.Failure(NullPointerException()).javaClass,
                    awaitItem().javaClass
                )
                cancelAndIgnoreRemainingEvents()

                Mockito.verify(movieService, Mockito.times(1))
                    .getMovieDetail(ArgumentMatchers.anyString(), ArgumentMatchers.anyString())
            }
        }
    }

    @Test
    fun testMovieListError() {

        runTest {

            `when`(movieService.getPopularMovies(anyString())).thenThrow(
                NullPointerException::class.java
            )

            viewModel.movieStateFlow.test {
                viewModel.getPopularMovies()
                assertEquals(com.lbg.model.ApiStatus.Idle, awaitItem())
                assertEquals(com.lbg.model.ApiStatus.Loading, awaitItem())
                assertEquals(
                    com.lbg.model.ApiStatus.Failure(NullPointerException()).javaClass,
                    awaitItem().javaClass
                )
                cancelAndIgnoreRemainingEvents()

                Mockito.verify(movieService, Mockito.times(1))
                    .getPopularMovies(anyString())
            }
        }
    }


}
package com.lbg.domain

import com.lbg.data.repository.MoviesRepository
import com.lbg.data.repository.network.MovieService
import com.lbg.data.repository.network.MovieServiceApi
import com.lbg.domain.usecases.GetMovieDetailUseCase
import com.lbg.domain.usecases.GetPopularMoviesUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
@ExperimentalCoroutinesApi
class UseCaseUnitTest {


    private val movieService = Mockito.mock(MovieService::class.java)
    private val movieServiceImpl = MovieServiceApi(movieService)
    private val repository = MoviesRepository(movieServiceImpl)
    private val id = "5"

    @Test
    fun testMovieDetails() {
        runTest {
            val detail = Mockito.mock(com.lbg.model.MovieDetail::class.java)
            Mockito.doReturn(detail).`when`(movieService)
                .getMovieDetail(ArgumentMatchers.anyString(), ArgumentMatchers.anyString())

            GetMovieDetailUseCase(repository).getMovieDetail(id).collect {
                assert(it == detail)
            }
        }
    }

    @Test
    fun testMovieList() {
        runTest {
            val list = Mockito.mock(com.lbg.model.MovieList::class.java)
            Mockito.doReturn(list).`when`(movieService)
                .getPopularMovies(ArgumentMatchers.anyString())
            GetPopularMoviesUseCase(repository).getPopularMovies().collect {
                assert(it == list)
            }
        }
    }
}
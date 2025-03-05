package com.lbg.data

import com.lbg.data.model.MovieDetail
import com.lbg.data.model.MovieList
import com.lbg.data.repository.MoviesRepositoryImpl
import com.lbg.data.repository.network.MovieService
import com.lbg.data.repository.network.MovieServiceApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
@ExperimentalCoroutinesApi
class MoviesRepositoryUnitTest {

    private val movieService = Mockito.mock(MovieService::class.java)
    private val movieServiceImpl = MovieServiceApi(movieService)
    private val repository = MoviesRepositoryImpl(movieServiceImpl)
    private val id = "5"

    @Test
    fun testMovieDetails() {
        runTest {
            val detail = Mockito.mock(MovieDetail::class.java)
            Mockito.doReturn(detail).`when`(movieService)
                .getMovieDetail(ArgumentMatchers.anyString(), ArgumentMatchers.anyString())
            assert(
                repository.getMovieDetail(
                    id,
                    id
                ).javaClass == Resource.Success(detail).javaClass
            )
        }
    }

    @Test
    fun testMovieList() {
        runTest {
            val list = Mockito.mock(MovieList::class.java)
            Mockito.doReturn(list).`when`(movieService)
                .getPopularMovies(ArgumentMatchers.anyString())
            assert(repository.getPopularMovies(id).javaClass == Resource.Success(list).javaClass)
        }
    }
}
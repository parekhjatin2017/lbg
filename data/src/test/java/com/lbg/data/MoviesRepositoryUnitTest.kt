package com.lbg.data

import com.lbg.data.model.MovieDetail
import com.lbg.data.model.MovieList
import com.lbg.data.repository.MoviesRepositoryImpl
import com.lbg.data.repository.network.MovieServiceApi
import com.lbg.domain.model.ApiStatus
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

    private val movieServiceImpl = Mockito.mock(MovieServiceApi::class.java)
    private val repository = MoviesRepositoryImpl(movieServiceImpl)
    private val id = "5"

    @Test
    fun testGetMovieDetailApiStatusSuccessReceived() {
        runTest {
            val detail = Mockito.mock(MovieDetail::class.java)
            Mockito.doReturn(detail).`when`(movieServiceImpl)
                .getMovieDetail(ArgumentMatchers.anyString(), ArgumentMatchers.anyString())
            assert(
                repository.getMovieDetail(
                    id,
                    id
                ).javaClass == ApiStatus.Success(detail).javaClass
            )
        }
    }

    @Test
    fun negativeTestGetMovieDetailErrorReceived() {

        runTest {
            Mockito.doThrow(IndexOutOfBoundsException(id)).`when`(movieServiceImpl)
                .getMovieDetail(ArgumentMatchers.anyString(), ArgumentMatchers.anyString())
            assert(
                repository.getMovieDetail(id, id) == ApiStatus.Error(id)
            )
        }
    }

    @Test
    fun testGetPopularMoviesApiStatusSuccessReceived() {
        runTest {
            val list = Mockito.mock(MovieList::class.java)
            Mockito.doReturn(list).`when`(movieServiceImpl)
                .getPopularMovies(ArgumentMatchers.anyString())
            assert(repository.getPopularMovies(id).javaClass == ApiStatus.Success(list).javaClass)
        }
    }

    @Test
    fun negativeTestGetPopularMoviesApiStatusErrorReceived() {
        runTest {
            Mockito.doThrow(RuntimeException(id)).`when`(movieServiceImpl)
                .getPopularMovies(ArgumentMatchers.anyString())
            assert(repository.getPopularMovies(id) == ApiStatus.Error(id))
        }
    }
}
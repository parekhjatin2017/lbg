package com.lbg.domain

import app.cash.turbine.test
import com.lbg.data.model.MovieDetail
import com.lbg.data.repository.MoviesRepositoryImpl
import com.lbg.data.repository.network.MovieService
import com.lbg.data.repository.network.MovieServiceApi
import com.lbg.domain.model.ApiStatus
import com.lbg.domain.model.FilmDetails
import com.lbg.domain.usecases.GetMovieDetailUseCase
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
class GetMovieDetailUseCaseTest {


    private val movieService = Mockito.mock(MovieService::class.java)
    private val movieServiceImpl = MovieServiceApi(movieService)
    private val repository = MoviesRepositoryImpl(movieServiceImpl)
    private val id = "5"

    @Test
    fun testMovieDetails() {
        runTest {
            val detail = Mockito.mock(MovieDetail::class.java, Mockito.RETURNS_DEEP_STUBS)
            Mockito.doReturn(detail).`when`(movieService)
                .getMovieDetail(ArgumentMatchers.anyString(), ArgumentMatchers.anyString())

            GetMovieDetailUseCase(repository).getMovieDetail(id).test {
                assertEquals(ApiStatus.Loading, awaitItem())
                assert(
                    ApiStatus.MovieDetailSuccess(Mockito.mock(FilmDetails::class.java)).javaClass
                            == awaitItem().javaClass
                )
                cancelAndIgnoreRemainingEvents()
            }
        }
    }

    @Test
    fun negativeTestMovieDetails() {
        runTest {
            Mockito.doReturn(null).`when`(movieService)
                .getMovieDetail(ArgumentMatchers.anyString(), ArgumentMatchers.anyString())

            GetMovieDetailUseCase(repository).getMovieDetail(id).test {
                assertEquals(ApiStatus.Loading, awaitItem())
                assert(ApiStatus.Error("").javaClass == awaitItem().javaClass)
                cancelAndIgnoreRemainingEvents()
            }
        }
    }
}
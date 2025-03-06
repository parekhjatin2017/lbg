package com.lbg.domain

import app.cash.turbine.test
import com.lbg.domain.model.ApiStatus
import com.lbg.domain.model.FilmDetails
import com.lbg.domain.repository.IMoviesRepository
import com.lbg.domain.usecases.GetMovieDetailUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito

@ExperimentalCoroutinesApi
class GetMovieDetailUseCaseTest {

    private val repository = Mockito.mock(IMoviesRepository::class.java)
    private val id = "5"

    @Test
    fun testGetMovieDetailSuccessReceived() {
        runTest {
            val detail = Mockito.mock(FilmDetails::class.java)
            Mockito.doReturn(ApiStatus.Success(detail)).`when`(repository)
                .getMovieDetail(anyString(), anyString())

            GetMovieDetailUseCase(repository)(id).test {
                assert(ApiStatus.Loading == awaitItem())
                assert(ApiStatus.Success(detail).javaClass == awaitItem().javaClass)
                cancelAndIgnoreRemainingEvents()
            }
        }
    }

    @Test
    fun negativeTestGetMovieDetailErrorReceived() {
        runTest {
            Mockito.doReturn(ApiStatus.Error(id)).`when`(repository)
                .getMovieDetail(anyString(), anyString())

            GetMovieDetailUseCase(repository)(id).test {
                assert(ApiStatus.Loading == awaitItem())
                assert(ApiStatus.Error(id) == awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
        }
    }

    @Test
    fun negativeTestGetMovieDetailNetworkErrorReceived() {
        runTest {
            Mockito.doReturn(ApiStatus.NetworkError).`when`(repository)
                .getMovieDetail(anyString(), anyString())

            GetMovieDetailUseCase(repository)(id).test {
                assert(ApiStatus.Loading == awaitItem())
                assert(ApiStatus.NetworkError == awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
        }
    }
}
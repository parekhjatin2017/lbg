package com.lbg.domain

import app.cash.turbine.test
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
    fun testMovieDetails() {
        runTest {
            val detail = Mockito.mock(FilmDetails::class.java, Mockito.RETURNS_DEEP_STUBS)
            Mockito.doReturn(Resource.Success(detail)).`when`(repository)
                .getMovieDetail(anyString(), anyString())

            GetMovieDetailUseCase(repository)(id).test {
                assert(ApiStatus.Loading == awaitItem())
                assert(
                    ApiStatus.MovieDetailSuccess(detail).javaClass
                            == awaitItem().javaClass
                )
                cancelAndIgnoreRemainingEvents()
            }
        }
    }

    @Test
    fun negativeTestMovieDetails() {
        runTest {
            Mockito.doReturn(Resource.Error<FilmDetails>("")).`when`(repository)
                .getMovieDetail(anyString(), anyString())

            GetMovieDetailUseCase(repository)(id).test {
                assert(ApiStatus.Loading == awaitItem())
                assert(ApiStatus.Error("").javaClass == awaitItem().javaClass)
                cancelAndIgnoreRemainingEvents()
            }
        }
    }
}
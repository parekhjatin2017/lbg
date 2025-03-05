package com.lbg.domain

import app.cash.turbine.test
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

    @Test
    fun testMovieList() {
        runTest {
            val list = listOf(Mockito.mock(Film::class.java))
            Mockito.doReturn(Resource.Success(list)).`when`(repository)
                .getPopularMovies(anyString())

            GetPopularMoviesUseCase(repository)().test {
                assert(ApiStatus.Loading == awaitItem())
                assert(
                    ApiStatus.MovieListSuccess(list).javaClass
                            == awaitItem().javaClass
                )
                cancelAndIgnoreRemainingEvents()
            }
        }
    }

    @Test
    fun negativeTestMovieList() {
        runTest {
            Mockito.doReturn(Resource.Error<List<Film>>("")).`when`(repository)
                .getPopularMovies(anyString())

            GetPopularMoviesUseCase(repository)().test {
                assert(ApiStatus.Loading == awaitItem())
                assert(ApiStatus.Error("").javaClass == awaitItem().javaClass)
                cancelAndIgnoreRemainingEvents()
            }
        }
    }
}
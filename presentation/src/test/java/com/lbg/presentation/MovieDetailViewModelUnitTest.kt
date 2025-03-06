package com.lbg.presentation

import app.cash.turbine.test
import com.lbg.domain.model.ApiStatus
import com.lbg.domain.model.FilmDetails
import com.lbg.domain.repository.IMoviesRepository
import com.lbg.domain.usecases.GetMovieDetailUseCase
import com.lbg.presentation.viewmodel.MovieDetailViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
@ExperimentalCoroutinesApi
class MovieDetailViewModelUnitTest {

    private val repository = Mockito.mock(IMoviesRepository::class.java)
    private val getMovieDetailUseCase = GetMovieDetailUseCase(repository)
    private val viewModel = MovieDetailViewModel(getMovieDetailUseCase)
    private val id = "5"

    @Test
    fun testGetMovieDetailApiStatusSuccess() {

        runTest {
            val detail = Mockito.mock(FilmDetails::class.java)
            Mockito.doReturn(ApiStatus.Success(detail)).`when`(repository)
                .getMovieDetail(anyString(), anyString())

            viewModel.movieDetailStateFlow.test {
                viewModel.getMovieDetail(id)
                assertEquals(ApiStatus.Idle, awaitItem())
                assertEquals(ApiStatus.Loading, awaitItem())
                assertEquals(
                    ApiStatus.Success(detail).javaClass, awaitItem().javaClass
                )
                cancelAndIgnoreRemainingEvents()
            }
        }
    }
}
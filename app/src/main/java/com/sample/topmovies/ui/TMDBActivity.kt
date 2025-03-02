package com.sample.topmovies.ui

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.lbg.domain.model.ApiStatus
import com.lbg.domain.model.Film
import com.lbg.presentation.screens.MovieList
import com.lbg.presentation.screens.OpenDetailScreen
import com.lbg.presentation.theme.CinemaTheme
import com.lbg.presentation.viewmodel.TMDBViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

@AndroidEntryPoint
class TMDBActivity : ComponentActivity() {

    private val tmdbViewModel: TMDBViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                runBlocking {
                    delay(SPLASH_DURATION_MS)
                }
                false
            }
        }
        tmdbViewModel.getPopularMovies()
        setContent {
            CinemaTheme {
                Box(
                    Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colors.background)
                ) {

                    val navController = rememberNavController()
                    NavHost(navController, startDestination = "home") {
                        composable("home") {
                            SetHomeScreen(tmdbViewModel, applicationContext, {
                                tmdbViewModel.getMovieDetail(it.id)
                            }) {
                                tmdbViewModel.getPopularMovies()
                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun SetHomeScreen(
        viewModel: TMDBViewModel,
        context: Context,
        clickAction: (Film) -> Unit,
        back: () -> Unit
    ) {

        when (val state = viewModel.movieStateFlow.collectAsState().value) {
            is ApiStatus.Loading -> {
                Toast.makeText(context, "Loading data...", Toast.LENGTH_SHORT).show()
            }

            is ApiStatus.Error -> {
                Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
            }

            is ApiStatus.MovieListSuccess -> {
                MovieList(state.data, clickAction)
            }

            is ApiStatus.MovieDetailSuccess -> {
                OpenDetailScreen(state.data, back)
            }

            is ApiStatus.Idle -> {}
        }

    }

    companion object {
        private const val SPLASH_DURATION_MS = 3000L
    }
}

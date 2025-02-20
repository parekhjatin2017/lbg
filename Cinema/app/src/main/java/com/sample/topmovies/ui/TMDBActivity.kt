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
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sample.topmovies.model.Movie
import com.sample.topmovies.ui.theme.JetpackcomposeTheme
import com.sample.topmovies.viewModel.ApiStatus
import com.sample.topmovies.viewModel.TMDBViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TMDBActivity : ComponentActivity() {

    private val tmdbViewModel: TMDBViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        tmdbViewModel.getPopularMovies()
        setContent {
            JetpackcomposeTheme {
                TransparentSystemBars()

                Box(
                    Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colors.background)
                ) {

                    val navController = rememberNavController()
                    NavHost(navController, startDestination = "home") {
                        composable("home") {
                            setHomeScreen(tmdbViewModel, applicationContext, {
                                tmdbViewModel.getMovieDetail(it.id.toString())
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
    private fun TransparentSystemBars() {
        val statusBarColor = MaterialTheme.colors.background
        SideEffect {
            window.statusBarColor = statusBarColor.toArgb()
            window.navigationBarColor = statusBarColor.toArgb()
        }
    }


    @Composable
    fun setHomeScreen(
        viewModel: TMDBViewModel,
        context: Context,
        clickAction: (Movie) -> Unit,
        back: () -> Unit
    ) {

        when (val state = viewModel.movieStateFlow.collectAsState().value) {
            is ApiStatus.Loading -> {
                Toast.makeText(context, "Loading data...", Toast.LENGTH_SHORT).show()
            }
            is ApiStatus.Failure -> {
                Toast.makeText(context, state.msg.message, Toast.LENGTH_SHORT).show()
                /*val activity = (LocalContext.current as? Activity)
                activity?.finish()*/
            }
            is ApiStatus.MovieListSuccess -> {
                MovieList(state.data.results, clickAction)
            }
            is ApiStatus.MovieDetailSuccess -> {
                //Toast.makeText(context, state.data.toString(), Toast.LENGTH_SHORT).show()
                OpenDetailScreen(state.data, back)
            }
            is ApiStatus.Idle -> {}
        }

    }
}

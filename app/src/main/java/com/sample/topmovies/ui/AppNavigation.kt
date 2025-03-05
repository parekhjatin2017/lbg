package com.sample.topmovies.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.lbg.presentation.screens.MovieList
import com.lbg.presentation.screens.OpenDetailScreen

private object AppDestinations {
    const val MOVIE_LIST_ROUTE = "movie_list"
    const val MOVIE_DETAIL_ROUTE = "movie_detail"
    const val MOVIE_DETAIL_ID_KEY = "movie_id"
}

@Composable
fun AppNavigation(
    startDestination: String = AppDestinations.MOVIE_LIST_ROUTE
) {
    val navController = rememberNavController()
    val actions = remember(navController) { AppActions(navController) }

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(
            AppDestinations.MOVIE_LIST_ROUTE
        ) {
            MovieList(selectedMovie = actions.selectedMovie)
        }
        composable(
            "${AppDestinations.MOVIE_DETAIL_ROUTE}/{${AppDestinations.MOVIE_DETAIL_ID_KEY}}",
            arguments = listOf(
                navArgument(AppDestinations.MOVIE_DETAIL_ID_KEY) {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val arguments = requireNotNull(backStackEntry.arguments)
            OpenDetailScreen(
                movieId = arguments.getString(AppDestinations.MOVIE_DETAIL_ID_KEY, ""),
                back = actions.navigateUp
            )
        }
    }
}

private class AppActions(
    navController: NavHostController
) {
    val selectedMovie: (String) -> Unit = { movieId: String ->
        navController.navigate("${AppDestinations.MOVIE_DETAIL_ROUTE}/$movieId")
    }
    val navigateUp: () -> Unit = {
        navController.navigateUp()
    }
}

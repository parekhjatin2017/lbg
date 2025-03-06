package com.lbg.presentation.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.lbg.domain.Constants
import com.lbg.domain.model.ApiStatus
import com.lbg.domain.model.Film
import com.lbg.presentation.R
import com.lbg.presentation.viewmodel.MovieListViewModel

@Composable
fun MovieList(
    viewModel: MovieListViewModel = hiltViewModel(),
    selectedMovie: (String) -> Unit,
    onErrorDismissRequest: () -> Unit
) {
    val context = LocalContext.current
    when (val state = viewModel.movieListStateFlow.collectAsState().value) {
        is ApiStatus.Success -> {
            Scaffold(topBar = {
                AppBar(
                    title = LocalContext.current.getString(R.string.app_name),
                    image = Icons.Filled.Home
                ) {}
            }) {
                Surface(modifier = Modifier.padding(it)) {
                    LazyColumn {
                        items(state.value) { film ->
                            CardView(film, selectedMovie)
                        }
                    }
                }
            }
        }

        is ApiStatus.Idle,
        is ApiStatus.Loading -> {
            LoadingDialog(context.getString(R.string.loading_movie_list))
        }

        is ApiStatus.Error -> {
            val error = state.error ?: context.getString(R.string.api_error)
            SimpleMessageDialog(
                context.getString(R.string.movie_list_api_error, error),
                onErrorDismissRequest
            )
        }

        is ApiStatus.NetworkError -> {
            SimpleMessageDialog(
                context.getString(R.string.movie_list_network_error),
                onErrorDismissRequest
            )
        }
    }

}

@Composable
fun CardView(film: Film, itemSelection: (String) -> Unit) {
    Card(
        modifier = Modifier
            .padding(top = 15.dp, start = 15.dp, end = 15.dp)
            .fillMaxWidth()
            .wrapContentHeight(align = Alignment.Top)
            .clickable { itemSelection(film.id) },
        elevation = 8.dp,
        backgroundColor = Color.White,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            DP(film, 70.dp)
            MovieName(film, Alignment.Start)
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun DP(film: Film, profilePicSize: Dp) {
    Card(
        shape = CircleShape,
        border = BorderStroke(
            width = 1.dp,
            color = if (film.isAdult)
                Color.Green
            else Color.Red
        ),
        modifier = Modifier.padding(16.dp),
        elevation = 4.dp
    ) {
        GlideImage(
            model = Constants.IMAGE_URL_W185 + film.posterPath,
            contentDescription = "",
            modifier = Modifier.size(profilePicSize),
            contentScale = ContentScale.Crop
        )
    }
}


@Composable
fun MovieName(film: Film, alignment: Alignment.Horizontal) {
    Column(
        modifier = Modifier
            .padding(8.dp),
        horizontalAlignment = alignment
    ) {
        CompositionLocalProvider(
            LocalContentAlpha provides if (film.isAdult)
                1f else ContentAlpha.medium
        ) {
            Text(text = film.title ?: "", style = MaterialTheme.typography.h6)
        }
        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            Text(
                text = film.releaseDate ?: "",
                style = MaterialTheme.typography.body2
            )
        }
    }
}

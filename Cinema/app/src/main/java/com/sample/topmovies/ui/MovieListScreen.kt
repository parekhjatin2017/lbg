package com.sample.topmovies.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.sample.topmovies.Constants
import com.sample.topmovies.model.Movie
import com.sample.topmovies.ui.theme.lightGreen

@Composable
fun MovieList(movieList: List<Movie>, itemSelection: (Movie) -> Unit) {

    Scaffold(topBar = {
        AppBar(
            title = "Cinema",
            image = Icons.Filled.Home
        ) {}
    }) {
        Surface(modifier = Modifier.padding(it)) {
            LazyColumn {
                items(movieList) { movie ->
                    CardView(movie, itemSelection)
                }
            }
        }
    }
}

@Composable
fun CardView(movie: Movie, itemSelection: (Movie) -> Unit) {
    Card(
        modifier = Modifier
            .padding(top = 15.dp, start = 15.dp, end = 15.dp)
            .fillMaxWidth()
            .wrapContentHeight(align = Alignment.Top)
            .clickable { itemSelection(movie) },
        elevation = 8.dp,
        backgroundColor = Color.White,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            DP(movie, 70.dp)
            MovieName(movie, Alignment.Start)
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun DP(movie: Movie, profilePicSize: Dp) {
    Card(
        shape = CircleShape,
        border = BorderStroke(
            width = 1.dp,
            color = if (movie.adult)
                MaterialTheme.colors.lightGreen
            else Color.Red
        ),
        modifier = Modifier.padding(16.dp),
        elevation = 4.dp
    ) {
            GlideImage(
            model = Constants.IMAGE_URL_W185 + movie.poster_path,
            contentDescription = "",
            modifier = Modifier.size(profilePicSize),
            contentScale = ContentScale.Crop
        )
    }
}


@Composable
fun MovieName(movie: Movie, alignment: Alignment.Horizontal) {
    Column(
        modifier = Modifier
            .padding(8.dp),
        horizontalAlignment = alignment
    ) {
        CompositionLocalProvider(
            LocalContentAlpha provides if (movie.adult)
                1f else ContentAlpha.medium
        ) {
            Text(text = movie.title, style = MaterialTheme.typography.h6)
        }
        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            Text(
                text = movie.release_date,
                style = MaterialTheme.typography.body2
            )
        }
    }
}

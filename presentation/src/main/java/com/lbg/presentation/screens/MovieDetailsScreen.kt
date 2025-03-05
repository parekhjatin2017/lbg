package com.lbg.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.lbg.domain.model.ApiStatus
import com.lbg.domain.model.FilmDetails
import com.lbg.domain.usecases.Constants
import com.lbg.presentation.viewmodel.MovieDetailViewModel

@Composable
fun OpenDetailScreen(
    viewmodel: MovieDetailViewModel = hiltViewModel(),
    movieId: String,
    back: () -> Unit
) {
    LaunchedEffect(Unit) {
        viewmodel.getMovieDetail(movieId)
    }

    when (val state = viewmodel.movieDetailStateFlow.collectAsState().value) {
        is ApiStatus.Success -> {
            Surface(modifier = Modifier.fillMaxSize()) {
                MovieDetail(state.value, back)
            }
        }

        is ApiStatus.Error -> {}
        is ApiStatus.Loading -> {}
        is ApiStatus.NetworkError -> {}
    }

}

@Composable
fun MovieDetail(film: FilmDetails, back: () -> Unit) {

    Scaffold(topBar = {
        AppBar(
            title = film.title ?: "",
            image = Icons.AutoMirrored.Filled.ArrowBack,
            back
        )
    }) {

        Surface(modifier = Modifier.padding(it)) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                MovieDetail(film)
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MovieDetail(filmDetails: FilmDetails) {
    Card(
        modifier = Modifier.padding(15.dp),
        elevation = 4.dp
    ) {
        GlideImage(
            model = Constants.IMAGE_URL_W780 + filmDetails.posterPath,
            contentDescription = "",
            contentScale = ContentScale.Crop
        )
        Text(
            modifier = Modifier.padding(2.dp),
            text = filmDetails.overview ?: "",
            color = Color.White,
            style = MaterialTheme.typography.body2
        )
    }
}

@Composable
fun AppBar(title: String, image: ImageVector, onClick: () -> Unit) {
    TopAppBar(
        title = {
            Text(
                text = title,
                color = Color.White
            )
        },
        navigationIcon = {
            IconButton(onClick = { onClick.invoke() }) {
                Icon(imageVector = image, contentDescription = "")
            }
        }
    )
}


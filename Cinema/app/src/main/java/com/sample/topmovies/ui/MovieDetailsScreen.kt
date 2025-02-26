package com.sample.topmovies.ui

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.sample.topmovies.Constants
import com.sample.topmovies.model.MovieDetail

@Composable
fun OpenDetailScreen(item : MovieDetail, back: () -> Unit){
    Surface(modifier = Modifier.fillMaxSize()) {
        MovieDetail(item, back)
    }
}

@Composable
fun MovieDetail(movie: MovieDetail, back: () -> Unit) {

    Scaffold(topBar = {
        AppBar(
            title = movie.title,
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
                MovieDetail(movieDetail = movie)
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MovieDetail(movieDetail: MovieDetail) {
    Card(
        modifier = Modifier.padding(15.dp),
        elevation = 4.dp
    ) {
        GlideImage(
            model = Constants.IMAGE_URL_W780 + movieDetail.poster_path,
            contentDescription = "",
            contentScale = ContentScale.Crop
        )
        Text(
            modifier = Modifier.padding(2.dp),
            text = movieDetail.overview,
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


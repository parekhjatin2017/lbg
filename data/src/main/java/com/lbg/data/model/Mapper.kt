package com.lbg.data.model

import com.lbg.domain.model.Film
import com.lbg.domain.model.FilmDetails


fun MovieList.toFilmList(): List<Film> {
    return ArrayList<Film>().apply {
        results.forEach { movie ->
            add(movie.toFilm())
        }
    }
}

private fun Movie.toFilm(): Film {
    return Film(id.toString(), title, adult, release_date, poster_path)
}

fun MovieDetail.toFilmDetail(): FilmDetails {
    return FilmDetails(title, overview, poster_path)
}
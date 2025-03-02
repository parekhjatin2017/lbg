package com.lbg.domain.model

data class Film(
    val id: String,
    val title: String?,
    val isAdult: Boolean,
    val releaseDate: String?,
    val posterPath: String?
)

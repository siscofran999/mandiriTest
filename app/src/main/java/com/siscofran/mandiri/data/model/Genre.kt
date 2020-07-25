package com.siscofran.mandiri.data.model

data class Genre(
    val genres: List<GenreX>
)

data class GenreX(
    val id: Int,
    val name: String
)
package com.siscofran.mandiri.data.model

data class Video(
    val id: Int,
    val results: List<ResultX>
)

data class ResultX(
    val id: String,
    val iso_3166_1: String,
    val iso_639_1: String,
    val key: String,
    val name: String,
    val site: String,
    val size: Int,
    val type: String
)
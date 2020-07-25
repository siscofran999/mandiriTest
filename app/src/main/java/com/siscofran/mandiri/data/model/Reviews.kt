package com.siscofran.mandiri.data.model

data class Reviews(
    val id: Int,
    val page: Int,
    val results: ArrayList<ResultY>,
    val total_pages: Int,
    val total_results: Int
)

data class ResultY(
    val author: String,
    val content: String,
    val id: String,
    val url: String
)
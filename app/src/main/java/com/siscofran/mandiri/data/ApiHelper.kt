package com.siscofran.mandiri.data

import com.siscofran.mandiri.data.model.*

interface ApiHelper {
    suspend fun getDiscover(idGenre : Int, page: Int): Discover
    suspend fun getGenre(): Genre
    suspend fun getDetail(movieId : Int): Detail
    suspend fun getVideos(movieId : Int): Video
    suspend fun getReviews(movieId : Int, page: Int): Reviews

}
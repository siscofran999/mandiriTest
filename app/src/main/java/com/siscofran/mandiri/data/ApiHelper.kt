package com.siscofran.mandiri.data

import com.siscofran.mandiri.data.model.*
import io.reactivex.Single

interface ApiHelper {
    fun getDiscover(idGenre : Int, page: Int): Single<Discover>
    fun getGenre(): Single<Genre>
    fun getDetail(movieId : Int): Single<Detail>
    fun getVideos(movieId : Int): Single<Video>
    fun getReviews(movieId : Int, page: Int): Single<Reviews>

}
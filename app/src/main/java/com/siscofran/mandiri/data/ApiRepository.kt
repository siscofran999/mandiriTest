package com.siscofran.mandiri.data

import com.siscofran.mandiri.data.model.*
import io.reactivex.Single
import javax.inject.Inject

class ApiRepository @Inject constructor(private val apiService: ApiService) : ApiHelper {

    override fun getDiscover(idGenre : Int, page: Int): Single<Discover> = apiService.getDiscover(idGenre, page)

    override fun getGenre(): Single<Genre> = apiService.getGenre()

    override fun getDetail(movieId: Int): Single<Detail> = apiService.getDetail(movieId)

    override fun getVideos(movieId: Int): Single<Video> = apiService.getVideos(movieId)

    override fun getReviews(movieId: Int, page: Int): Single<Reviews> = apiService.getReviews(movieId, page)

}
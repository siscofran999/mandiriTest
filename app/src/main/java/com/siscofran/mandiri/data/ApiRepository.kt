package com.siscofran.mandiri.data

import com.siscofran.mandiri.data.model.*
import javax.inject.Inject

class ApiRepository @Inject constructor(private val apiService: ApiService) : ApiHelper {

    override suspend fun getDiscover(idGenre : Int, page: Int) : Discover = apiService.getDiscover(idGenre, page)

    override suspend fun getGenre(): Genre = apiService.getGenre()

    override suspend fun getDetail(movieId: Int): Detail = apiService.getDetail(movieId)

    override suspend fun getVideos(movieId: Int): Video = apiService.getVideos(movieId)

    override suspend fun getReviews(movieId: Int, page: Int): Reviews = apiService.getReviews(movieId, page)

}
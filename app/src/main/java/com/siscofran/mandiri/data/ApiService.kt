package com.siscofran.mandiri.data

import com.siscofran.mandiri.data.model.*
import com.siscofran.mandiri.util.Link.API_KEY
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("discover/movie?api_key=$API_KEY&language=en-US&sort_by=popularity.desc&include_adult=false&include_video=true")
    fun getDiscover(
        @Query("with_genres") idGenre : Int,
        @Query("page") page : Int
    ): Single<Discover>

    @GET("genre/movie/list?api_key=$API_KEY&language=en-US")
    fun getGenre(): Single<Genre>

    @GET("movie/{movie_id}?api_key=$API_KEY&language=en-US")
    fun getDetail(
        @Path("movie_id") movieId : Int
    ): Single<Detail>

    @GET("movie/{movie_id}/videos?api_key=$API_KEY&language=en-US")
    fun getVideos(
        @Path("movie_id") movieId : Int
    ): Single<Video>

    @GET("movie/{movie_id}/reviews?api_key=$API_KEY&language=en-US")
    fun getReviews(
        @Path("movie_id") movieId : Int,
        @Query("page") page : Int
    ): Single<Reviews>

}
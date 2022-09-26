package com.fachrul.api_service.service.remote

import com.fachrul.api_service.service.Const
import com.fachrul.common.entity.movie_detail.MovieDetailResponse
import com.fachrul.common.entity.movie_review.MovieReviewResponse
import com.fachrul.common.entity.movie_video.MovieVideoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieDetailService {
    @GET("movie/{id}")
    suspend fun getMovieDetail(
        @Path("id") movieId:Long,
        @Query("api_key") api:String = Const.API_KEY
    ): Response<MovieDetailResponse>

    @GET("movie/{id}/reviews")
    suspend fun getReviews(
        @Path("id")movieId:Long,
        @Query("api_key") api:String = Const.API_KEY,
        @Query("page")page:Int = 1
    ) : Response<MovieReviewResponse>

    @GET("movie/{id}/videos")
    suspend fun getMovieVideos(
        @Path("id") id:Long,
        @Query("api_key")api_key:String = Const.API_KEY,
    ): Response<MovieVideoResponse>

}
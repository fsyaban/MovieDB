package com.fachrul.api_service.service.remote

import com.fachrul.api_service.service.Const
import com.fachrul.common.entity.discover.DiscoverMovieResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface DiscoverMovieService {
    @GET("discover/movie")
    suspend fun getDiscoverMovie(
        @Query("api_key")apiKey:String = Const.API_KEY,
        @Query("with_genres") genres :String?,
        @Query("page")page:Int=1
    ):Response<DiscoverMovieResponse>

    @GET("discover/movie")
    fun getDiscoverMovieCall(
        @Query("api_key")apiKey:String = Const.API_KEY,
        @Query("page")page:Int=1
    ): Call<DiscoverMovieResponse>

    @GET("movie/top_rated")
    fun getTopRatedMovie(
        @Query("api_key")apiKey:String = Const.API_KEY
    ): Call<DiscoverMovieResponse>


    @GET("trending/movie/week")
    suspend fun getTrendingMovies(
        @Query("api_key")apiKey:String = Const.API_KEY,
        @Query("page")page:Int=1
    ):Response<DiscoverMovieResponse>

    @GET("trending/movie/week")
    fun getTrendingMovieCall(
        @Query("api_key")apiKey:String = Const.API_KEY,
    ):Call<DiscoverMovieResponse>

    @GET("search/movie")
    suspend fun getSearchMovies(
        @Query("api_key")apiKey:String = Const.API_KEY,
        @Query("query")query:String
    ):Response<DiscoverMovieResponse>
}
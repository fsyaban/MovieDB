package com.fachrul.api_service.service.remote

import com.fachrul.api_service.service.Const
import com.fachrul.common.entity.genre.GenreResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GenreService {

    @GET("genre/movie/list")
    suspend fun getGenre(
        @Query("api_key") api:String = Const.API_KEY
    ):Response<GenreResponse>

}
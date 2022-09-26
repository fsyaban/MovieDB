package com.fachrul.api_service.usecase

import com.fachrul.api_service.service.remote.MovieDetailService
import com.fachrul.common.entity.Result
import kotlinx.coroutines.flow.flow

class MovieDetailUseCase(val movieDetailService: MovieDetailService) {
    operator fun invoke(id: Long) = flow {
        try {
            emit(Result.Loading)
            val detailResponse = movieDetailService.getMovieDetail(id)
            if (detailResponse.isSuccessful) {
                val videoResponse = movieDetailService.getMovieVideos(id)
                if (videoResponse.isSuccessful) {
                    detailResponse.body()?.let { detail ->
                        videoResponse.body()?.let { video ->
                            emit(Result.Success(Pair(detail, video)))
                        }
                    }
                }
            } else{
                emit(Result.Error(detailResponse.message()))
            }
        } catch (e: Exception) {
            emit(Result.Error(e.toString()))
        }
    }
}
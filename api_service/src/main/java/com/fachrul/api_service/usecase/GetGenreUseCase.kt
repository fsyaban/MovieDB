package com.fachrul.api_service.usecase

import com.fachrul.api_service.service.remote.GenreService
import com.fachrul.common.entity.Result
import kotlinx.coroutines.flow.flow

class GetGenreUseCase(private val genreService: GenreService) {
    operator fun invoke() = flow {
        emit(Result.Loading)
        try {
            val response = genreService.getGenre()
            if (response.isSuccessful){
                response.body()?.let {
                    emit(Result.Success(it.genres))
                }
            } else {
                emit(Result.Error(response.message()))
            }
        } catch (e:Exception){
            emit(Result.Error(e.toString()))
        }
    }
}
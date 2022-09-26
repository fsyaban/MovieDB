package com.fachrul.api_service.repository

import androidx.lifecycle.LiveData
import com.fachrul.api_service.service.local.FavoriteDao
import com.fachrul.common.entity.database.MovieEntity
import com.fachrul.common.ext.AppExecutors

class FavoriteRepository(
    private val favoriteDao: FavoriteDao,
    private val appExecutors: AppExecutors
) {
    fun getAllFavorit(): LiveData<List<MovieEntity>> = favoriteDao.getAllFavorit()

    fun toggle(movie: MovieEntity) {
        appExecutors.diskIO.execute {
            if (favoriteDao.isMovieExists(movie.movieId))
                favoriteDao.deleteFavorit(movie.movieId)
            else favoriteDao.insertFavorit(movie)
        }
    }

    fun isMovieFavorite(id:Long): LiveData<Boolean> =  favoriteDao.isMovieFavorite(id)

}

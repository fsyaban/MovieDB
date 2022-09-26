package com.fachrul.api_service.service.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.fachrul.common.entity.database.MovieEntity

@Dao
interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertFavorit(movieEntity: MovieEntity)

    @Query("DELETE FROM movie WHERE status = 'favorite' AND movie_id=:id")
    fun deleteFavorit(id:Long)

    @Query("SELECT * from movie WHERE status = 'favorite' ORDER BY id ASC")
    fun getAllFavorit(): LiveData<List<MovieEntity>>

    @Query("SELECT EXISTS(SELECT * FROM movie WHERE movie_id = :id AND status='favorite')")
    fun isMovieExists(id: Long):Boolean

    @Query("SELECT EXISTS(SELECT * FROM movie WHERE movie_id = :id AND status= 'favorite')")
    fun isMovieFavorite(id: Long): LiveData<Boolean>
}
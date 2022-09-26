package com.fachrul.api_service.service.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.fachrul.common.entity.database.MovieEntity

@Dao
interface DiscoverMovieDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertMovie(movie:MovieEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertMovies(movies: List<MovieEntity>)

    @Delete
    fun deleteMovie(movie: MovieEntity)

    @Query("SELECT * from movie  WHERE status = 'discover' ORDER BY movie_id ASC")
    fun getAllMovie(): LiveData<List<MovieEntity>>

    @Query("DELETE FROM movie WHERE status = 'discover'")
    fun deleteAllMovie()



}
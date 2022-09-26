package com.fachrul.api_service.service.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.fachrul.common.common.BaseFragment
import com.fachrul.common.entity.database.MovieEntity

@Dao
interface TrendingMovieDao:DiscoverMovieDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    override fun insertMovie(trendingEntity: MovieEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    override fun insertMovies(movies: List<MovieEntity>)

    @Delete
    override fun deleteMovie(movie: MovieEntity)

    @Query("SELECT * from movie WHERE status = 'trending' ORDER BY id ASC")
    override fun getAllMovie(): LiveData<List<MovieEntity>>

    @Query("DELETE FROM movie WHERE status = 'trending'")
    override fun deleteAllMovie()
}
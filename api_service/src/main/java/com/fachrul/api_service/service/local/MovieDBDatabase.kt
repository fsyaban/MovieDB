package com.fachrul.api_service.service.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.fachrul.common.entity.database.MovieEntity

@Database(entities = [MovieEntity::class], version = 1, exportSchema = false)
abstract class MovieDBDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao
    abstract fun movieDao(): DiscoverMovieDao
    abstract fun trendingDao(): TrendingMovieDao
    abstract fun upcomingDao(): TopRatedMovieDao

    companion object {
        @Volatile
        private var instance: MovieDBDatabase? = null
        fun getInstance(context: Context): MovieDBDatabase =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    MovieDBDatabase::class.java, "MovieDB.db"
                ).build()
            }
    }
}
package com.fachrul.moviedb.di.module

import android.content.Context
import com.fachrul.api_service.service.local.MovieDBDatabase
import com.fachrul.common.ext.AppExecutors
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun providesDatabase(@ApplicationContext context: Context) = MovieDBDatabase.getInstance(context)

    @Provides
    @Singleton
    fun providesFavoritDao(movieDBDatabase: MovieDBDatabase) = movieDBDatabase.favoriteDao()

    @Provides
    @Singleton
    fun providesMovieDao(movieDBDatabase: MovieDBDatabase) = movieDBDatabase.movieDao()

    @Provides
    @Singleton
    fun providesTrendingMovieDao(movieDBDatabase: MovieDBDatabase) = movieDBDatabase.trendingDao()

    @Provides
    @Singleton
    fun providesUpcomingMovieDao(movieDBDatabase: MovieDBDatabase) = movieDBDatabase.upcomingDao()

    @Provides
    @Singleton
    fun providesExecutor() = AppExecutors()



}
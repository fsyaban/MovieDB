package com.fachrul.moviedb.di.module

import android.content.Context
import com.fachrul.api_service.service.RetrofitClient
import com.fachrul.api_service.service.remote.DiscoverMovieService
import com.fachrul.api_service.service.remote.GenreService
import com.fachrul.api_service.service.remote.MovieDetailService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

    @Provides
    @Singleton
    fun provideRetrofit() = RetrofitClient.getClient()

    @Provides
    @Singleton
    fun provideGenreService(retrofit: Retrofit) =
        retrofit.create(GenreService::class.java)

    @Provides
    @Singleton
    fun provideDiscoverMovieService(retrofit: Retrofit) =
        retrofit.create(DiscoverMovieService::class.java)

    @Provides
    @Singleton
    fun provideMovieDetailService(retrofit: Retrofit) =
        retrofit.create(MovieDetailService::class.java)


}
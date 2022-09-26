package com.fachrul.moviedb.di.module

import com.fachrul.api_service.repository.FavoriteRepository
import com.fachrul.api_service.repository.MovieRepository
import com.fachrul.api_service.service.local.DiscoverMovieDao
import com.fachrul.api_service.service.local.FavoriteDao
import com.fachrul.api_service.service.local.TopRatedMovieDao
import com.fachrul.api_service.service.local.TrendingMovieDao
import com.fachrul.api_service.service.remote.DiscoverMovieService
import com.fachrul.api_service.service.remote.GenreService
import com.fachrul.api_service.service.remote.MovieDetailService
import com.fachrul.api_service.usecase.*
import com.fachrul.common.ext.AppExecutors
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class UseCaseModule {

    @Provides
    fun providesGenreUseCase(genreService: GenreService) =
        GetGenreUseCase(genreService)

    @Provides
    fun providesDiscoverUseCase(discoverMovieService: DiscoverMovieService) =
        GetDiscoverMoviePagingUseCase(discoverMovieService)

    @Provides
    fun providesMovieReviewUseCase(movieReviewService: MovieDetailService) =
        GetMovieReviewPagingUseCase(movieReviewService)


    @Provides
    fun providesDetailUseCase(movieVideoService: MovieDetailService) =
        MovieDetailUseCase(movieVideoService)


    @Provides
    fun providesTrendingUseCase(discoverMovieService: DiscoverMovieService) =
        GetTrendingMoviePagingUseCase(discoverMovieService)

    @Provides
    fun providesSearchUseCase(discoverMovieService: DiscoverMovieService) =
        GetSearchMovieUseCase(discoverMovieService)

    @Provides
    fun providesFavoriteRepository(favoriteDao: FavoriteDao, executors: AppExecutors) =
        FavoriteRepository(favoriteDao, executors)

    @Provides
    fun providesMovieRepository(
        discoverMovieService: DiscoverMovieService,
        discoverMovieDao: DiscoverMovieDao,
        trendingMovieDao: TrendingMovieDao,
        topRatedMovieDao: TopRatedMovieDao,
        executors: AppExecutors
    ) =
        MovieRepository(
            discoverMovieService,
            discoverMovieDao,
            trendingMovieDao,
            topRatedMovieDao,
            executors
        )

}
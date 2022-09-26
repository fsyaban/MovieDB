package com.fachrul.api_service.usecase

import com.fachrul.api_service.paging.TrendingMoviesPager
import com.fachrul.api_service.service.remote.DiscoverMovieService
import com.fachrul.common.entity.Result
import kotlinx.coroutines.flow.flow

class GetTrendingMoviePagingUseCase(val discoverMovieService: DiscoverMovieService) {
    operator fun invoke() =
        TrendingMoviesPager.createPager(10,discoverMovieService).flow
}
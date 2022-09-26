package com.fachrul.api_service.usecase

import com.fachrul.api_service.paging.DiscoverMoviesPager
import com.fachrul.api_service.service.remote.DiscoverMovieService

class GetDiscoverMoviePagingUseCase (
    private val discoverMovieService: DiscoverMovieService
) {
    operator fun invoke(args: String?) =
        DiscoverMoviesPager.createPager(10, discoverMovieService, args).flow
}
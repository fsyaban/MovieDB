package com.fachrul.api_service.usecase

import com.fachrul.api_service.paging.DiscoverMoviesPager
import com.fachrul.api_service.paging.SearchMoviesPager
import com.fachrul.api_service.service.remote.DiscoverMovieService
import kotlinx.coroutines.flow.flow

class GetSearchMovieUseCase (val discoverMovieService: DiscoverMovieService){
    operator fun invoke(query:String) =
        SearchMoviesPager.createPager(10, discoverMovieService, query).flow

}
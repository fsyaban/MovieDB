package com.fachrul.api_service.usecase

import com.fachrul.api_service.paging.MovieReviewPager
import com.fachrul.api_service.service.remote.MovieDetailService

class GetMovieReviewPagingUseCase (
    private val movieReviewService: MovieDetailService
) {
    operator fun invoke(movieId:Long) =
        MovieReviewPager.createPager(10, movieReviewService, movieId).flow
}
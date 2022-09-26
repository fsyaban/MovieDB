package com.fachrul.api_service.paging

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.fachrul.api_service.service.remote.MovieDetailService
import com.fachrul.common.entity.movie_review.Result

class MovieReviewDataSource(
    private val movieReviewService: MovieDetailService,
    private val movieId:Long
):PagingSource<Int,Result>() {
    override fun getRefreshKey(state: PagingState<Int, Result>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Result> {
        return try {
            val result = movieReviewService.getReviews(
                movieId = movieId, page = params.key ?: 1
            )
            result.body()?.let {
                val totalPage = it.totalPages
                LoadResult.Page(data = it.results, if (it.page == 1) null else it.page - 1,
                    if(it.page<totalPage)it.page + 1 else null)
            } ?: LoadResult.Error(Exception("invalid data"))
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}

object MovieReviewPager{
    fun createPager(
        pageSize:Int,
        movieReviewService: MovieDetailService,
        movieId: Long
    ):Pager<Int,Result> =
        Pager(
            config = PagingConfig(pageSize),
            pagingSourceFactory = { MovieReviewDataSource(movieReviewService,movieId) }
        )
}
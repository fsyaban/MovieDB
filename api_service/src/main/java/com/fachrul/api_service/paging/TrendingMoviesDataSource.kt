package com.fachrul.api_service.paging

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.fachrul.api_service.service.remote.DiscoverMovieService
import com.fachrul.common.entity.discover.Result

class TrendingMoviesDataSource(
    private val discoverMovieService: DiscoverMovieService,
) : PagingSource<Int, Result>() {
    override fun getRefreshKey(state: PagingState<Int, Result>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Result> {
        return try {
            val result = discoverMovieService.getTrendingMovies(
                page = params.key?:1
            )
            val page = params.key?:1
            val prevPage = if(page==1)null else page-1
            if (result.isSuccessful){
                result.body()?.let {
                    val nextPage = if (it.results.isEmpty() || page>=it.totalPages) null else page+1
                    LoadResult.Page(data = it.results, prevPage,nextPage)
                }?: LoadResult.Error(Exception("Error Backend: ${result.code()}"))
            } else{
                LoadResult.Error(Exception("Error: ${result.message()}"))
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}

object TrendingMoviesPager {
    fun createPager(
        pageSize: Int,
        discoverMovieService: DiscoverMovieService,
    ): Pager<Int, Result> = Pager(
        config = PagingConfig(pageSize, initialLoadSize = pageSize),
        pagingSourceFactory = {
            TrendingMoviesDataSource(discoverMovieService)
        }
    )
}
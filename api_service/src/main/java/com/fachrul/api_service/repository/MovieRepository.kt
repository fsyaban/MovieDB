package com.fachrul.api_service.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.fachrul.api_service.service.Const
import com.fachrul.api_service.service.local.DiscoverMovieDao
import com.fachrul.api_service.service.local.TrendingMovieDao
import com.fachrul.api_service.service.local.TopRatedMovieDao
import com.fachrul.api_service.service.remote.DiscoverMovieService
import com.fachrul.common.entity.database.MovieEntity
import com.fachrul.common.ext.AppExecutors
import com.fachrul.common.entity.Result
import com.fachrul.common.entity.discover.DiscoverMovieResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MovieRepository(
    val discoverMovieService: DiscoverMovieService,
    val discoverMovieDao: DiscoverMovieDao,
    val trendingMovieDao: TrendingMovieDao,
    val topRatedMovieDao: TopRatedMovieDao,
    val appExecutors: AppExecutors
) {
    private val result = MediatorLiveData<Result<List<MovieEntity>>>()
    private val resultTrending = MediatorLiveData<Result<List<MovieEntity>>>()
    private val resultUpcoming = MediatorLiveData<Result<List<MovieEntity>>>()

    fun getDiscoverMovies(): LiveData<Result<List<MovieEntity>>> {
        return getResult(
            discoverMovieService.getDiscoverMovieCall(),
            result,
            discoverMovieDao,
            "discover"
        )
    }

    fun getTrendingMovies(): LiveData<Result<List<MovieEntity>>> {
        return getResult(
            discoverMovieService.getTrendingMovieCall(),
            resultTrending,
            trendingMovieDao,
            "trending"
        )
    }

    fun getTopRatedMovies(): LiveData<Result<List<MovieEntity>>> {
        return getResult(
            discoverMovieService.getTopRatedMovie(),
            resultUpcoming,
            topRatedMovieDao,
            "toprated"
        )
    }

    private fun getResult(
        client: Call<DiscoverMovieResponse>,
        resultMediator: MediatorLiveData<Result<List<MovieEntity>>>,
        movieDao: DiscoverMovieDao,
        status: String
    ): MediatorLiveData<Result<List<MovieEntity>>> {
        resultMediator.value = Result.Loading
        client.enqueue(object : Callback<DiscoverMovieResponse> {
            override fun onResponse(
                call: Call<DiscoverMovieResponse>,
                response: Response<DiscoverMovieResponse>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        val movies = it.results
                        val movieList = ArrayList<MovieEntity>()
                        appExecutors.networkIO.execute {
                            movies.forEach { m ->
                                val movie = MovieEntity(
                                    m.title,
                                    m.voteAverage,
                                    m.releaseDate,
                                    m.genreIds.joinToString(separator = ", ") {
                                        Const.getGenreString(
                                            it
                                        )
                                    },
                                    m.posterPath?:"",
                                    m.backdropPath?:"",
                                    status,
                                    m.id
                                )
                                movieList.add(movie)
                            }
                            movieDao.deleteAllMovie()
                            movieDao.insertMovies(movieList)
                        }
                    }
                } else {
                    resultMediator.value = Result.Error(response.message())
                }
            }

            override fun onFailure(call: Call<DiscoverMovieResponse>, t: Throwable) {
                resultMediator.value = Result.Error(t.toString())
            }

        })
        val localdata = movieDao.getAllMovie()
        resultMediator.addSource(localdata) {
            if (it.isNotEmpty()) resultMediator.value = Result.Success(it)
        }
        return resultMediator
    }

}

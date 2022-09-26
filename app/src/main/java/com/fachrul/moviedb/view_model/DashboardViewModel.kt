package com.fachrul.moviedb.view_model

import android.app.Application
import androidx.lifecycle.LiveData
import com.fachrul.api_service.repository.MovieRepository
import com.fachrul.common.common.BaseViewModel
import com.fachrul.common.entity.database.MovieEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.fachrul.common.entity.Result as Res

@HiltViewModel
class DashboardViewModel @Inject constructor(
    application: Application,
    private val movieRepository: MovieRepository
) : BaseViewModel(application) {

    var discoverState: LiveData<Res<List<MovieEntity>>>? = null
    var trendingMovieState: LiveData<Res<List<MovieEntity>>>? = null
    var topRatedMovieState: LiveData<Res<List<MovieEntity>>>? = null


    fun getMovies() {
        discoverState = movieRepository.getDiscoverMovies()
        trendingMovieState = movieRepository.getTrendingMovies()
        topRatedMovieState = movieRepository.getTopRatedMovies()
    }

}
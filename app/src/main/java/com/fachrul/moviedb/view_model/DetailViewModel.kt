package com.fachrul.moviedb.view_model

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.fachrul.api_service.usecase.GetMovieReviewPagingUseCase
import com.fachrul.api_service.usecase.MovieDetailUseCase
import com.fachrul.api_service.repository.FavoriteRepository
import com.fachrul.common.common.BaseViewModel
import com.fachrul.common.entity.Result
import com.fachrul.common.entity.database.MovieEntity
import com.fachrul.common.entity.movie_detail.MovieDetailResponse
import com.fachrul.common.entity.movie_video.MovieVideoResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.fachrul.common.entity.movie_review.Result as reviewResult

@HiltViewModel
class DetailViewModel @Inject constructor(
    application: Application,
    val detailUseCase: MovieDetailUseCase,
    val reviewUseCase: GetMovieReviewPagingUseCase,
    val favoriteRepository: FavoriteRepository

) : BaseViewModel(application) {
    val detailState = MutableLiveData<Result<Pair<MovieDetailResponse, MovieVideoResponse>>>()
    val reviewState = MutableLiveData<PagingData<reviewResult>>()
    var isFavoriteLiveData: LiveData<Boolean>? = null

    fun fetchMovieDetail(id: Long) {
        viewModelScope.launch {
            detailUseCase.invoke(id).collect {
                detailState.postValue(it)
            }
        }
        viewModelScope.launch {
            reviewUseCase.invoke(id).collect {
                reviewState.postValue(it)
            }
        }
        isFavoriteLiveData = favoriteRepository.isMovieFavorite(id)
    }

    fun toggle(result: MovieDetailResponse) {
        viewModelScope.launch {
            favoriteRepository.toggle(
                MovieEntity(
                    result.title,
                    result.voteAverage,
                    result.releaseDate,
                    result.genres.joinToString(separator = ", ") { it.name },
                    result.posterPath,
                    result.backdropPath,
                    "favorite",
                    result.id
                )
            )
        }
    }

}
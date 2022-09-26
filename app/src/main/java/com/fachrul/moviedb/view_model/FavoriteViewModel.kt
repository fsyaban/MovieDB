package com.fachrul.moviedb.view_model

import android.app.Application
import androidx.lifecycle.LiveData
import com.fachrul.api_service.repository.FavoriteRepository
import com.fachrul.common.common.BaseViewModel
import com.fachrul.common.entity.database.MovieEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    application: Application,
    val favoriteRepository: FavoriteRepository
) : BaseViewModel(application) {
    var favoriteState: LiveData<List<MovieEntity>>?=null

    init {
        getFavorite()
    }
    fun getFavorite(){
        favoriteState = favoriteRepository.getAllFavorit()
    }
}
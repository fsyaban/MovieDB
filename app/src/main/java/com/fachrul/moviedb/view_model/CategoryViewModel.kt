package com.fachrul.moviedb.view_model

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.fachrul.api_service.usecase.GetGenreUseCase
import com.fachrul.common.common.BaseViewModel
import com.fachrul.common.entity.Result
import com.fachrul.common.entity.genre.Genre
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    application: Application,
    val genreUseCase: GetGenreUseCase
) : BaseViewModel(application) {
    val selectedGenre = arrayListOf<Genre>()
    val genreState = MutableLiveData<Result<List<Genre>>>()

    init {
       fetchGenre()
    }


    fun fetchGenre(){
        viewModelScope.launch {
            genreUseCase().collect(){
                genreState.postValue(it)
            }
        }
    }
}
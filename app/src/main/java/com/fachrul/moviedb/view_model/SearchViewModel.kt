package com.fachrul.moviedb.view_model

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.fachrul.api_service.usecase.GetSearchMovieUseCase
import com.fachrul.common.common.BaseViewModel
import com.fachrul.common.entity.discover.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    application: Application,
    val getSearchMovieUseCase: GetSearchMovieUseCase
) :
    BaseViewModel(application) {
        val searchMovieState = MutableLiveData<PagingData<Result>>()

    fun fetchMovie(query:String?){
        viewModelScope.launch {
            getSearchMovieUseCase.invoke(query?:"").cachedIn(viewModelScope).collect(){
                searchMovieState.postValue(it)
            }
        }
    }
}
package com.fachrul.moviedb.view_model

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.fachrul.api_service.usecase.GetDiscoverMoviePagingUseCase
import com.fachrul.common.common.BaseViewModel

import com.fachrul.common.entity.discover.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DiscoverViewModel @Inject constructor(
    application: Application,
    val discoverMoviePagingUseCase: GetDiscoverMoviePagingUseCase
) : BaseViewModel(application) {
    val discoverState = MutableLiveData<PagingData<Result>>()

    fun fetchDiscover(genres:String){
        viewModelScope.launch {
            discoverMoviePagingUseCase(genres).collect{
                discoverState.postValue(it)
            }
        }
    }
}
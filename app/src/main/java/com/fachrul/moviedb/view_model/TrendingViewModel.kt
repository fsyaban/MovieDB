package com.fachrul.moviedb.view_model

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.fachrul.api_service.usecase.GetTrendingMoviePagingUseCase
import com.fachrul.common.common.BaseViewModel
import com.fachrul.common.entity.discover.Result
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrendingViewModel @Inject constructor(
    application: Application,
    val getTrendingMoviePagingUseCase: GetTrendingMoviePagingUseCase
) :
    BaseViewModel(application) {

    val trendingState = MutableLiveData<PagingData<Result>>()

    fun fetchTrending(){
        viewModelScope.launch {
            getTrendingMoviePagingUseCase.invoke().collect{
                trendingState.postValue(it)
            }
        }
    }
}
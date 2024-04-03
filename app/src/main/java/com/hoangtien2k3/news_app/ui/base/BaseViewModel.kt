package com.hoangtien2k3.news_app.ui.base

import androidx.lifecycle.MutableLiveData
import com.hoangtien2k3.news_app.utils.Resource
import retrofit2.Call

interface BaseViewModel {
    fun <T> performAction(resultLiveData: MutableLiveData<Resource<T>>, apiCall: () -> Call<T>)
}
package com.hoangtien2k3.news_app.ui.football

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hoangtien2k3.news_app.data.models.Football
import com.hoangtien2k3.news_app.data.source.api.FootballClient
import com.hoangtien2k3.news_app.network.ApiResponse
import com.hoangtien2k3.news_app.ui.base.BaseViewModelImpl
import com.hoangtien2k3.news_app.utils.Resource
import retrofit2.Call


class FootballViewModel : BaseViewModelImpl() {
    private val _footballNews = MutableLiveData<Resource<ApiResponse<List<Football>>>>()
    val footballNews: LiveData<Resource<ApiResponse<List<Football>>>> = _footballNews

    init {
        this.fetchDataCallAPI()
    }

    private fun callApiFootball(): Call<ApiResponse<List<Football>>> {
        return FootballClient.apiService.getFootballData()
    }

    fun fetchDataCallAPI() {
        performAction(_footballNews) {
            callApiFootball()
        }
    }

}

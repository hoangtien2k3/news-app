package com.hoangtien2k3.news_app.ui.home.fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hoangtien2k3.news_app.data.source.api.AppNewsService
import com.hoangtien2k3.news_app.data.source.api.RetrofitBase
import com.hoangtien2k3.news_app.network.ApiResponse
import com.hoangtien2k3.news_app.network.UserResponse
import com.hoangtien2k3.news_app.ui.base.BaseViewModelImpl
import com.hoangtien2k3.news_app.utils.Resource
import retrofit2.Call

class HomeViewModel : BaseViewModelImpl() {
    private val _accessTokenKey = MutableLiveData<Resource<ApiResponse<UserResponse>>>()
    val accessTokenKey: LiveData<Resource<ApiResponse<UserResponse>>> = _accessTokenKey

    val apiService = RetrofitBase.apiService(AppNewsService::class.java)

    init {
        this.fetchDataCallAPI()
    }

    private fun callApiMyInfo(): Call<ApiResponse<UserResponse>> {
        return apiService.myinfo()
    }

    private fun fetchDataCallAPI() {
        performAction(_accessTokenKey) {
            callApiMyInfo()
        }
    }
}
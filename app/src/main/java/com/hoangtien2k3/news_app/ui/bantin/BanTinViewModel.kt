package com.hoangtien2k3.news_app.ui.bantin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hoangtien2k3.news_app.data.models.BanTin
import com.hoangtien2k3.news_app.data.source.api.AppNewsService
import com.hoangtien2k3.news_app.data.source.api.RetrofitBase
import com.hoangtien2k3.news_app.network.ApiResponse
import com.hoangtien2k3.news_app.ui.base.BaseViewModelImpl
import com.hoangtien2k3.news_app.utils.Constants
import com.hoangtien2k3.news_app.utils.Resource

class BanTinViewModel : BaseViewModelImpl() {
    private val _banTinNews = MutableLiveData<Resource<ApiResponse<List<BanTin>>>>()
    val listTinTuc: LiveData<Resource<ApiResponse<List<BanTin>>>> = _banTinNews

    val apiService = RetrofitBase.apiService(AppNewsService::class.java)

    fun fetchDataCallAPI(banTin: String) {
        if (banTin == Constants.full) {
            performAction(_banTinNews) {
                apiService.getFullBanTin()
            }
        }
        performAction(_banTinNews) {
            apiService.getBanTin(banTin)
        }
    }
}



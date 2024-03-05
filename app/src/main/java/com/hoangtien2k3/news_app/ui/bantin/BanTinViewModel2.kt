package com.hoangtien2k3.news_app.ui.bantin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hoangtien2k3.news_app.data.models.BanTin
import com.hoangtien2k3.news_app.data.source.api.BanTinDemo
import com.hoangtien2k3.news_app.data.source.api.BanTinInstance
import com.hoangtien2k3.news_app.data.source.api.BanTinService
import com.hoangtien2k3.news_app.data.source.api.RetrofitInstance
import com.hoangtien2k3.news_app.utils.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BanTinViewModel2 : ViewModel() {

    private val _banTinNews = MutableLiveData<List<BanTin>>()
    val banTinNews: LiveData<List<BanTin>> = _banTinNews

    private var baseUrl: String = ""

    init {
        fetchBanTinNews()
    }

    fun fetchListBanTin(url: String?) {
//        baseUrl = url
    }

    fun fetchBanTinNews() {
        // Fetch BanTin news data using Retrofit
//        val service = BanTinInstance(Constants.BASE_URL_NEWS_LOCAL, BanTinService::class.java).apiServiceBanTin
        val service = BanTinDemo.apiServiceBanTin
        val call = service.getBanTinData()

        call.enqueue(object : Callback<List<BanTin>> {
            override fun onResponse(call: Call<List<BanTin>>, response: Response<List<BanTin>>) {
                if (response.isSuccessful) {
                    val footballList = response.body() ?: emptyList()
                    _banTinNews.value = footballList
                }
            }

            override fun onFailure(call: Call<List<BanTin>>, t: Throwable) {
                // Handle failure
            }
        })
    }

}
package com.hoangtien2k3.news_app.ui.bantin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hoangtien2k3.news_app.data.models.BanTin
import com.hoangtien2k3.news_app.data.source.api.BanTinDemo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BanTinViewModel2 : ViewModel() {

    private val _banTinNews = MutableLiveData<List<BanTin>>()
    val listTinTuc: LiveData<List<BanTin>> = _banTinNews

    init {
        fetchListTinTuc()
    }

    fun fetchListTinTuc() {
        val service = BanTinDemo.apiService
        val call = service.getBanTinData()

        call.enqueue(object : Callback<List<BanTin>> {
            override fun onResponse(call: Call<List<BanTin>>, response: Response<List<BanTin>>) {
                if (response.isSuccessful) {
                    val footballList = response.body() ?: emptyList()
                    _banTinNews.value = footballList
                }
            }

            override fun onFailure(call: Call<List<BanTin>>, t: Throwable) {

            }
        })
    }

}
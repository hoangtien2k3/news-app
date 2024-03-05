package com.hoangtien2k3.news_app.data.source.api

import com.hoangtien2k3.news_app.data.models.BanTin
import retrofit2.Call
import retrofit2.http.GET

interface BanTinService {
    @GET("/news")
    fun getBanTinData(): Call<List<BanTin>>
}
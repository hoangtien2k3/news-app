package com.hoangtien2k3.news_app.data.source.api

import com.hoangtien2k3.news_app.data.models.BanTin
import com.hoangtien2k3.news_app.network.ApiResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface BanTinService {
    @GET("api/news/category/{category}")
    fun getBanTin(@Path("category") category: String?): Call<ApiResponse<List<BanTin>>>

    @GET("/api/news/full")
    fun getFullBanTin(): Call<ApiResponse<List<BanTin>>>
}
package com.hoangtien2k3.news_app.data.source.api

import com.hoangtien2k3.news_app.utils.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object BanTinClient {
    private const val BASE_URL = Constants.BASE_URL_LOCAL
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiService: BanTinService by lazy {
        retrofit.create(BanTinService::class.java)
    }
}
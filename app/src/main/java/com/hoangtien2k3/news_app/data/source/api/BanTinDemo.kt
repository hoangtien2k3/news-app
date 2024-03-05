package com.hoangtien2k3.news_app.data.source.api

import com.hoangtien2k3.news_app.utils.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object BanTinDemo {
    private val retrofitBanTin: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL_NEWS_LOCAL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiServiceBanTin: BanTinService by lazy {
        retrofitBanTin.create(BanTinService::class.java)
    }
}
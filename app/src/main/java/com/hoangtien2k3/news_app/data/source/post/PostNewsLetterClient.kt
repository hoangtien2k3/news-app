package com.hoangtien2k3.news_app.data.source.post

import com.hoangtien2k3.news_app.data.source.auth.AccountService
import com.hoangtien2k3.news_app.utils.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object PostNewsLetterClient {
    private const val BASE_URL = Constants.BASE_URL_LOCAL
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiService: PostNewsLetterService by lazy {
        retrofit.create(PostNewsLetterService::class.java)
    }
}
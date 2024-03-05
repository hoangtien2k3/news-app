package com.hoangtien2k3.news_app.data.source.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class BanTinInstance<T>(
    private val url: String,
    private val serviceClass: Class<T>
) {
    companion object {
        private fun createRetrofitInstance(url: String): Retrofit {
            return Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        fun <T> createInstance(url: String, serviceClass: Class<T>): T {
            val retrofit = createRetrofitInstance(url)
            return retrofit.create(serviceClass)
        }
    }

    val apiServiceBanTin: T by lazy {
        createInstance(url, serviceClass)
    }
}


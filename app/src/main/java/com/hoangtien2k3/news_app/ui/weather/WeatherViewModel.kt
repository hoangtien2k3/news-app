package com.hoangtien2k3.news_app.ui.weather

import androidx.lifecycle.MutableLiveData
import com.hoangtien2k3.news_app.data.source.api.AppNewsService
import com.hoangtien2k3.news_app.data.source.api.RetrofitBase
import com.hoangtien2k3.news_app.network.response.WeatherResponse
import com.hoangtien2k3.news_app.ui.base.BaseViewModelImpl
import com.hoangtien2k3.news_app.utils.Constants
import com.hoangtien2k3.news_app.utils.Resource

class WeatherViewModel : BaseViewModelImpl() {
    private val _weatherResponse = MutableLiveData<Resource<WeatherResponse>>()
    val weatherResponse: MutableLiveData<Resource<WeatherResponse>> = _weatherResponse

    val apiService = RetrofitBase.apiService(AppNewsService::class.java)

    fun getCurrentWeatherData(city: String) {
        val apiKey = Constants.apiKeyWeather
        performAction(_weatherResponse) {
            apiService.getCurrentWeather(city, apiKey)
        }
    }
}

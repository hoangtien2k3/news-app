package com.hoangtien2k3.news_app.ui.weather

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hoangtien2k3.news_app.data.source.api.WeatherService
import com.hoangtien2k3.news_app.network.response.WeatherResponse
import com.hoangtien2k3.news_app.utils.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WeatherViewModel : ViewModel() {

    private val _weatherResponse = MutableLiveData<WeatherResponse?>()
    val weatherResponse: MutableLiveData<WeatherResponse?> = _weatherResponse

    private val retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL_Weather)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val service = retrofit.create(WeatherService::class.java)
    private val apiKey = Constants.apiKeyWeather

    fun getCurrentWeatherData(city: String) {
        val call = service.getCurrentWeather(city, apiKey)
        call.enqueue(object : Callback<WeatherResponse> {
            override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
                if (response.isSuccessful) {
                    val weatherResponse = response.body()
                    _weatherResponse.value = weatherResponse
                }
            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }
}

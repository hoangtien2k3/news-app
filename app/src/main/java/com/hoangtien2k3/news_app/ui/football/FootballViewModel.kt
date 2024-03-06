package com.hoangtien2k3.news_app.ui.football

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hoangtien2k3.news_app.data.models.Football
import com.hoangtien2k3.news_app.data.source.api.FootballClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FootballViewModel : ViewModel() {

    private val _footballNews = MutableLiveData<List<Football>>()
    val footballNews: LiveData<List<Football>> = _footballNews

    init {
        fetchFootballNews()
    }

    fun fetchFootballNews() {
        val service = FootballClient.apiService
        val call = service.getFootballData()

        call.enqueue(object : Callback<List<Football>> {
            override fun onResponse(call: Call<List<Football>>, response: Response<List<Football>>) {
                if (response.isSuccessful) {
                    val footballList = response.body() ?: emptyList()
                    _footballNews.value = footballList
                }
            }

            override fun onFailure(call: Call<List<Football>>, t: Throwable) {

            }
        })
    }

}

//package com.hoangtien2k3.news_app.ui.home.viewmodel
//
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.ViewModel
//import com.hoangtien2k3.news_app.data.models.Football
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response
//import androidx.lifecycle.viewModelScope
//import com.hoangtien2k3.news_app.data.source.api.FootballClient
//import kotlinx.coroutines.launch
//import kotlinx.coroutines.Dispatchers
//
//class HomeViewModel : ViewModel() {
//    private val _footballNews = MutableLiveData<List<Football>>()
//    val footballNews: LiveData<List<Football>> = _footballNews
//
//    init {
//        fetchFootballNews()
//    }
//
//    private fun fetchFootballNews() {
//        viewModelScope.launch(Dispatchers.IO) {
//            val service = FootballClient.apiService
//            val call = service.getFootballData()
//
//            call.enqueue(object : Callback<List<Football>> {
//                override fun onResponse(call: Call<List<Football>>, response: Response<List<Football>>) {
//                    if (response.isSuccessful) {
//                        val footballList = response.body() ?: emptyList()
//                        _footballNews.postValue(footballList)
//                    }
//                }
//
//                override fun onFailure(call: Call<List<Football>>, t: Throwable) {
//
//                }
//            })
//        }
//    }
//
//}

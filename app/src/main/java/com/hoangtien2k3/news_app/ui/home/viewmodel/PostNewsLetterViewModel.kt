package com.hoangtien2k3.news_app.ui.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hoangtien2k3.news_app.data.source.api.AppNewsService
import com.hoangtien2k3.news_app.data.source.api.RetrofitBase
import com.hoangtien2k3.news_app.network.request.PostNewsLetterRequest
import com.hoangtien2k3.news_app.network.response.PostNewsLetterResponse
import com.hoangtien2k3.news_app.ui.base.BaseViewModelImpl
import com.hoangtien2k3.news_app.utils.Resource

class PostNewsLetterViewModel : BaseViewModelImpl() {
    private val _postNewsLetterResult = MutableLiveData<Resource<PostNewsLetterResponse>>()
    val postNewsLetterResult: LiveData<Resource<PostNewsLetterResponse>> = _postNewsLetterResult

    val apiService = RetrofitBase.apiService(AppNewsService::class.java)

    fun postNewsLetter(title: String, link: String, img: String, pubDate: String, category: String) {
        val postNewsLetterRequest = PostNewsLetterRequest(title, link, img, pubDate, category)
        performAction(_postNewsLetterResult) {
            apiService.postNews(postNewsLetterRequest)
        }
    }

}
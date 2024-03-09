package com.hoangtien2k3.news_app.ui.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hoangtien2k3.news_app.data.source.post.PostNewsLetterClient
import com.hoangtien2k3.news_app.network.request.PostNewsLetterRequest
import com.hoangtien2k3.news_app.network.response.PostNewsLetterResponse
import com.hoangtien2k3.news_app.network.result.PostNewsLetterResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostNewsLetterViewModel : ViewModel() {

    private val _postNewsLetterResult = MutableLiveData<PostNewsLetterResult>()
    val postNewsLetterResult: LiveData<PostNewsLetterResult> = _postNewsLetterResult

    fun postNewsLetter(title: String, link: String, img: String, pubDate: String, category: String) {
        val postNewsLetterRequest = PostNewsLetterRequest(title, link, img, pubDate, category)
        val service = PostNewsLetterClient.apiService
        val call: Call<PostNewsLetterResponse> = service.postNews(postNewsLetterRequest)

        call.enqueue(object : Callback<PostNewsLetterResponse> {
            override fun onResponse(call: Call<PostNewsLetterResponse>, response: Response<PostNewsLetterResponse>) {
                if (response.isSuccessful) {
                    val postNewsLetterResponse = response.body()
                    _postNewsLetterResult.value = postNewsLetterResponse?.let {
                        PostNewsLetterResult.Success(it)
                    }
                } else {
//                    _postNewsLetterResult.value = PostNewsLetterResult.Error("Failed to post newsletter")
                }
            }

            override fun onFailure(call: Call<PostNewsLetterResponse>, t: Throwable) {
//                _postNewsLetterResult.value = PostNewsLetterResult.Error("Network error")
            }
        })
    }

}
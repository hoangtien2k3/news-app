package com.hoangtien2k3.news_app.network.result

import com.hoangtien2k3.news_app.network.response.PostNewsLetterResponse

sealed class PostNewsLetterResult {
    data class Success(val postNewsLetterResponse: PostNewsLetterResponse) : PostNewsLetterResult()
    data class Error(val postNewsLetterResponse: PostNewsLetterResponse) : PostNewsLetterResult()
}
package com.hoangtien2k3.news_app.network.result

import com.hoangtien2k3.news_app.network.response.SignupResponse

sealed class SignupResult {
    data class Success(val signupResponse: SignupResponse) : SignupResult()
    data class Error(val message: String) : SignupResult()
}
package com.hoangtien2k3.news_app.response

import com.hoangtien2k3.news_app.data.models.LoginResponse

sealed class LoginResult {
    data class Success(val loginResponse: LoginResponse) : LoginResult()
    data class Error(val message: String) : LoginResult()
}
